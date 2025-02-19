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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maber01
 */
public class Configuration
{
  private File homedir;
  private File ffmpegdir;
  private File ffmpegexec;
  private File ffmpegprobeexec;
  private File temporiginalvideofile;
  private File tempfilteredvideofile;
  private File tempplaceholderimagefile;

  private File propFile;
  Properties properties;
  
  public static Configuration prepHome()
  {
    Configuration config = new Configuration();
    
    config.homedir = new File( System.getProperty("user.home"), ".pptsquash" );
    if ( !config.homedir.exists() )
      config.homedir.mkdirs();
    
    config.propFile = new File( config.homedir, "prefs.properties" );
    config.properties = new Properties();
    if ( config.propFile.exists() )
    {
      try ( FileReader reader = new FileReader( config.propFile ) )
      {
        config.properties.load( reader );
      }
      catch ( IOException ex )
      {
      }
    }
    
    config.temporiginalvideofile = new File( config.homedir, "temporiginal.mp4" );
    if ( config.temporiginalvideofile.exists() )
      config.temporiginalvideofile.delete();
    config.tempfilteredvideofile = new File( config.homedir, "tempfiltered.mp4" );
    if ( config.tempfilteredvideofile.exists() )
      config.tempfilteredvideofile.delete();
    config.tempplaceholderimagefile = new File( config.homedir, "tempplaceholder.png" );
    config.ffmpegdir = new File( config.homedir, "ffmpeg" );
    if ( !config.ffmpegdir.exists() )
      config.ffmpegdir.mkdirs();
    config.ffmpegexec = new File( config.ffmpegdir, "ffmpeg.exe" );
    config.ffmpegprobeexec = new File( config.ffmpegdir, "ffprobe.exe" );
    
    for ( File f : new File[] {config.ffmpegexec, config.ffmpegprobeexec} )
    {
      if ( !f.exists() )
      {
        try ( InputStream is = Configuration.class.getClassLoader().getResourceAsStream("os_windows/ffmpeg/" + f.getName() );
              FileOutputStream os = new FileOutputStream( f ) )
        {
          is.transferTo( os );
        }
        catch ( IOException ex )
        {
          Logger.getLogger( PptVideoStripFrame.class.getName() ).log( Level.SEVERE, null, ex );
        }
      }
    }
    return config;
  }

  private void saveProperties()
  {
    try ( FileWriter writer = new FileWriter( propFile ) )
    {
      properties.store( writer, "PPt Squasher Properties" );
    }
    catch ( IOException ex )
    {
    }    
  }
  
  public File getPreferredDirectory()
  {
    String s = properties.getProperty( "directory" );
    if ( s != null )
    {
      File f = new File( s );
      if ( f.exists() && f.isDirectory() )
        return f;
    }
    return new File( System.getProperty( "user.home" ) );
  }
  
  public void setPreferredDirectory( File f )
  {
    properties.setProperty( "directory", f.toString() );
    saveProperties();
  }
  
  public File getHomedir()
  {
    return homedir;
  }

  public File getFfmpegexec()
  {
    return ffmpegexec;
  }

  public File getFfmpegprobeexec()
  {
    return ffmpegprobeexec;
  }

  public File getTemporiginalvideofile()
  {
    return temporiginalvideofile;
  }

  public File getTempfilteredvideofile()
  {
    return tempfilteredvideofile;
  }

  public File getTempplaceholderimagefile()
  {
    return tempplaceholderimagefile;
  }
  
  
}
