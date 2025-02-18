/*
 * Copyright 2025 maber01.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this infile except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leedsbeckett.pptsquash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author maber01
 */
public class PowerPointProcesssor implements Runnable
{
  public static final int STATUS_WAITING        =0;
  public static final int STATUS_ANALYSING      =1;
  public static final int STATUS_PROCESSING     =2;
  public static final int STATUS_ERROR          =3;
  public static final int STATUS_ANALYSED       =4;
  public static final int STATUS_PROCESSED      =5;
  
  private int status = STATUS_WAITING;
  private Thread thread;
  private final Configuration config;
  private final AnalyserListener listener;
  private final FfmpegRunner ffmpegrunner;
  private final FfProbeRunner ffproberunner;
  private File infile, outfile;

  private int videoCount=0;
  private long videoBytes=0L;
  private int current;
  
  public PowerPointProcesssor( Configuration config, AnalyserListener listener )
  {
    this.config = config;
    this.listener = listener;
    ffmpegrunner = new FfmpegRunner( config, listener );
    ffproberunner = new FfProbeRunner( config );
  }

  public void kill()
  {
    ffmpegrunner.kill();
  }
  
  public void reset()
  {
    status = STATUS_WAITING;
    listener.analyserStatusChange();
    videoCount=0;
  }
  
  public void analyse( File file )
  {
    this.infile = file;
    status = STATUS_ANALYSING;
    listener.analyserStatusChange();
    thread = new Thread( this );
    thread.start();
  }
  
  public void process( File infile, File outfile )
  {
    this.infile = infile;
    this.outfile = outfile;
    status = STATUS_PROCESSING;
    listener.analyserStatusChange();
    thread = new Thread( this );
    thread.start();
  }
  
  public int getStatus()
  {
    return status;
  }

  public File getInfile()
  {
    return infile;
  }

  public File getOutfile()
  {
    return outfile;
  }

  public int getVideoCount()
  {
    return videoCount;
  }

  public long getVideoBytes()
  {
    return videoBytes;
  }
  
  
  private void runAnalysis() throws FileNotFoundException, IOException
  {
    videoCount=0;
    videoBytes=0L;
    try ( ZipInputStream zis = new ZipInputStream(new FileInputStream(infile)) )
    {
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null)
      {
        if ( zipEntry.getName().endsWith( ".mp4" ) )
        {
          listener.analysisProgressFile( zipEntry.getName(), zipEntry.getSize() );
          System.out.println( zipEntry.getName() );
          videoCount++;
          videoBytes+=zipEntry.getSize();
        }
        zipEntry = zis.getNextEntry();
      }
      zis.closeEntry();
      zis.close();
    }
    status = STATUS_ANALYSED;
    listener.analyserStatusChange();
  }

  private void duplicateEntry( ZipInputStream zis, ZipOutputStream zos, ZipEntry inZipEntry ) throws IOException
  {
    ZipEntry outZipEntry = new ZipEntry( inZipEntry );
    zos.putNextEntry( outZipEntry );
        
    byte[] buffer = new byte[1024];
    int len;
    while ( (len = zis.read( buffer )) > 0 )
      zos.write( buffer, 0, len );

    zos.closeEntry();
    zis.closeEntry();
  }
  
  private void filterEntry( ZipInputStream zis, ZipOutputStream zos, ZipEntry inZipEntry ) throws IOException, InterruptedException
  {
            
    listener.processingProgressFile( current++, 0L );
    ZipEntry outZipEntry = new ZipEntry( inZipEntry.getName() );
    zos.putNextEntry( outZipEntry );

    // Stream zip input to temp file.
    try ( FileOutputStream vforigout = new FileOutputStream( config.temporiginalvideofile ) )
    {
      zis.transferTo( vforigout );
    }
    VideoInformation vi = this.ffproberunner.runFfProbe( config.temporiginalvideofile );
    if ( vi == null )
      throw new IOException( "Unable to analyse video stream." );
    System.out.println( "Video width    " + vi.getWidth() );
    System.out.println( "Video height   " + vi.getHeight() );
    System.out.println( "Video duration " + vi.getDurationSecs() );
    if ( !vi.hasEnoughInformation() )
      throw new IOException( "Unable to parse video information in MP4 clip." );
    
    PlaceholderImageCreator.createPlaceholderImage( "png", config.tempplaceholderimagefile, vi.getWidth(), vi.getHeight() );
    
    int exitCode = this.ffmpegrunner.runFfmpeg( vi, config.temporiginalvideofile, config.tempplaceholderimagefile, config.tempfilteredvideofile );
    listener.processingProgressFile( current-1, config.tempfilteredvideofile.length() );
    config.temporiginalvideofile.delete();
    if ( exitCode != 0 )
    {
      config.tempfilteredvideofile.delete();
      throw new IOException( "Unable to process video." );
    }
    
    // Stream output temp file to zip output.
    try ( FileInputStream vffiltin = new FileInputStream( config.tempfilteredvideofile ) )
    {
      vffiltin.transferTo( zos );
    }
    config.tempfilteredvideofile.delete();

    zos.closeEntry();
    zis.closeEntry();
  }
  
  private void runProcessing() throws FileNotFoundException, IOException, InterruptedException
  {        
    current = 0;
    try ( ZipInputStream zis  = new ZipInputStream(  new FileInputStream(   infile ) );
          ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( outfile ) ) )
    {
      ZipEntry inZipEntry = zis.getNextEntry();
      while (inZipEntry != null)
      {
        if ( inZipEntry.getName().endsWith( ".mp4" ) )
          filterEntry( zis, zos, inZipEntry );
        else
          duplicateEntry( zis, zos, inZipEntry );

        inZipEntry = zis.getNextEntry();
      }
      zis.closeEntry();
      zis.close();
    }
    status = STATUS_PROCESSED;
    listener.analyserStatusChange();
  }

  
  @Override
  public void run()
  {
    try
    {
      if ( status == STATUS_ANALYSING )
        runAnalysis();
      if ( status == STATUS_PROCESSING )
        runProcessing();
    }
    catch ( IOException | InterruptedException ex )
    {
      Logger.getLogger(PowerPointProcesssor.class.getName() ).log( Level.SEVERE, null, ex );
      status = STATUS_ERROR;
      listener.analyserStatusChange();
      return;
    }
    
    System.out.println( "Complete" );
  }

}
