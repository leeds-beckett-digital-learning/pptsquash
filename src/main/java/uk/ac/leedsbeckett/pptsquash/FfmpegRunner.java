/*
 * Copyright 2025 maber01.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maber01
 */
public class FfmpegRunner
{
  public static final int AUDIO_MODE_PASS                  = 0;
  public static final int AUDIO_MODE_MODERATE_COMPRESSION  = 1;
  public static final int AUDIO_MODE_AGRESSIVE_COMPRESSION = 2;
  public static final int AUDIO_MODE_REPLACE               = 3;
  
  public static final int VIDEO_MODE_AGRESSIVE_COMPRESSION = 0;
  public static final int VIDEO_MODE_REPLACE               = 1;
  
  Configuration config;
  ExecutorService executor = Executors.newFixedThreadPool(10);
  AnalyserListener listener;
  Process process = null;

  public FfmpegRunner( Configuration config, AnalyserListener listener )
  {
    this.config   = config;
    this.listener = listener;
  }
  
  private int parseDuration( String str, int def )
  {
    String[] parts = str.split( ":" );
    if ( parts.length != 3 ) return def;
    String[] secparts = parts[2].split( "\\." );
    if ( secparts.length == 0 ) return def;
    
    int d = 0;
    d += Integer.parseInt( parts[0] )    * 100 * 60 * 60;
    d += Integer.parseInt( parts[1] )    * 100 * 60;
    d += Integer.parseInt( secparts[0] ) * 100;
    d += Integer.parseInt( secparts[1].substring( 0, 2 ) );
    return d;
  }
  
  
  public ArrayList<String> commandArguments( 
          VideoInformation vi, 
          File videoin, 
          File placeholder, 
          File videoout,
          int audioMode,
          int videoMode ) throws IOException, InterruptedException
  {
    int outputindex = 0;
    ArrayList<String> maplist = new ArrayList<>();
    ArrayList<String> commandlist = new ArrayList<>();
    commandlist.add( config.ffmpegexec.getAbsolutePath() );

    // Automatic yes to overwrite question.
    commandlist.add( "-y" );
    // Progress output
    commandlist.add( "-stats_period" );
    commandlist.add( "1" );

    if ( audioMode == AUDIO_MODE_REPLACE )
    {
      commandlist.add( "-f" );
      commandlist.add( "lavfi" );      
      commandlist.add( "-i" );
      commandlist.add( "anullsrc" );
      maplist.add( "-map" );    
      maplist.add( "" + outputindex + ":0" );
      outputindex++;      
    }

    // Placeholder file
    if ( videoMode == VIDEO_MODE_REPLACE )
    {
      commandlist.add( "-i" );
      commandlist.add( placeholder.getAbsolutePath() );
      maplist.add( "-map" );    
      maplist.add( "" + outputindex + ":0" );
      outputindex++;
    }
    

    if ( videoMode != VIDEO_MODE_REPLACE || audioMode != AUDIO_MODE_REPLACE )
    {
      // Input file
      commandlist.add( "-i" );
      commandlist.add( videoin.getAbsolutePath() );
      if ( audioMode != AUDIO_MODE_REPLACE )
      {
        maplist.add( "-map" );
        maplist.add( "" + outputindex + ":" + vi.getAudioIndex() );
      }
      if ( videoMode != VIDEO_MODE_REPLACE )
      {
        maplist.add( "-map" );
        maplist.add( "" + outputindex + ":" + vi.getVideoIndex() );
      }
      outputindex++;
    }

    // Now add the mappings;
    commandlist.addAll( maplist );

    commandlist.add( "-vcodec" );    
    commandlist.add( "libx264" );
    commandlist.add( "-crf" );
    commandlist.add( "100" );

    // Extra video encoding options if input is still image
    if ( videoMode == VIDEO_MODE_REPLACE )
    {
      commandlist.add( "-tune" );    
      commandlist.add( "stillimage" );
      commandlist.add( "-pix_fmt" );    
      commandlist.add( "yuv420p" );
    }
    
    // Audio Processing...
    if ( audioMode == AUDIO_MODE_PASS )
    {
      commandlist.add( "-c:a" );    
      commandlist.add( "copy" );    
    }
    else
    {
      commandlist.add( "-acodec" );
      commandlist.add( "aac" );
      commandlist.add( "-vbr" );
      if ( audioMode == AUDIO_MODE_MODERATE_COMPRESSION )
        commandlist.add( "1" );
      else
        commandlist.add( "3" );
    }
    
    // Duration pegged to audio stream if there is one
    // or video stream or two seconds
    Float audioDuration = ( audioMode == AUDIO_MODE_REPLACE ) ? null : vi.getAudioDurationSecs();
    Float videoDuration = ( videoMode == VIDEO_MODE_REPLACE ) ? null : vi.getVideoDurationSecs();
    commandlist.add( "-t" );
    if ( audioDuration != null )
      commandlist.add( Float.toString( audioDuration ) );
    else if ( videoDuration != null )
      commandlist.add( Float.toString( videoDuration ) );
    else
      commandlist.add( "2.0" );
    
    // Output file
    commandlist.add( videoout.getAbsolutePath() );
    
    for ( String s : commandlist )
      System.out.print( s + " " );
    System.out.println();
    
    return commandlist;
  }  
  
  /**
   * Run ffmpeg to process one input video to one output video.
   * Cannot operate by streaming because ffmpeg needs a seekable
   * output. (Needs to output blank header fields and then seek
   * back to enter correct values after completing the encoding.)
   * 
   * @param vi
   * @param videoin
   * @param placeholder
   * @param videoout
   * @param audioMode
   * @param videoMode
   * @return 
   * @throws IOException
   * @throws InterruptedException 
   */
  public int runFfmpeg( 
          VideoInformation vi, 
          File videoin, 
          File placeholder, 
          File videoout,
          int audioMode,
          int videoMode ) throws IOException, InterruptedException
  {
    ArrayList<String> commandlist = commandArguments( vi, videoin, placeholder, videoout, audioMode, videoMode );
    
    ProcessBuilder pb = new ProcessBuilder();
    pb.command( commandlist );
    process = pb.start();

    BufferedReader reader = new BufferedReader( new InputStreamReader( process.getErrorStream() ) );
    String line;
    int durationcsecs=Math.round( vi.getVideoDurationSecs() * 100.0f );
    System.out.println( "Duration " + durationcsecs );
    while( (line = reader.readLine()) != null )
    {
      if ( line.startsWith( "frame=" ) )
      {
        int i = line.indexOf( " time=" );
        if ( i>0 )
        {
          String value = line.substring( i + 6 );
          i = value.indexOf( " " );
          value = (i>=0) ? value.substring( 0, i ) : value;
          int t = parseDuration( value, -1 );
          if ( t >= 0 )
          {
            int pertenthou = 10000 * t / durationcsecs;
            if ( pertenthou > 10000 ) pertenthou = 10000;
            if ( listener != null )
              listener.processingProgress( pertenthou );
          }
        }
      }
      System.out.println( line );
    }
    
    int exitCode = process.waitFor();
    System.out.println( "ffmpeg exit code " + exitCode );
    if ( listener != null )
      listener.processingProgress( 10000 );
    process = null;
    return exitCode;
  }
  
  public void kill()
  {
    if ( process != null )
      process.destroyForcibly();
  }
  
  public static void main( String[] args )
  {
    Configuration config = Configuration.prepHome();
    FfmpegRunner ff = new FfmpegRunner( config, null );
    String home = System.getProperty("user.home");
    File vinf  = new File( home, "temporiginal.mp4" );
    File vph  = new File( home, "test.png" );
    File voutf = new File( home, "tempfiltered.mp4" );
    try
    {
      ff.runFfmpeg( null, vinf, vph, voutf, 0, 0 );
    }
    catch ( IOException | InterruptedException ex )
    {
      Logger.getLogger( FfmpegRunner.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }
}
