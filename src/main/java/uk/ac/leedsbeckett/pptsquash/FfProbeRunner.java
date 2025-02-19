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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author maber01
 */
public class FfProbeRunner
{
  Configuration config;
  ExecutorService executor = Executors.newFixedThreadPool(10);

  public FfProbeRunner( Configuration config )
  {
    this.config   = config;
  }
    
  
  /**
   * 
   * @param videoin
   * @throws IOException
   * @throws InterruptedException 
   */
  public VideoInformation runFfProbe( File videoin ) throws IOException, InterruptedException
  {
    try
    {
      VideoInformation vi = new VideoInformation();
      
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      
      ArrayList<String> commandlist = new ArrayList<>();
      commandlist.add( config.getFfmpegprobeexec().getAbsolutePath() );
      commandlist.add( "-of" );
      commandlist.add( "xml" );
      commandlist.add( "-show_streams" );
      commandlist.add( videoin.getAbsolutePath() );
      
      ProcessBuilder pb = new ProcessBuilder();
      pb.command( commandlist );
      Process process = pb.start();

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Streamer streamer = new Streamer( process.getInputStream(), baos );
      executor.submit( streamer );
              
      BufferedReader reader = new BufferedReader( new InputStreamReader( process.getErrorStream() ) );
      String line;
      while( (line = reader.readLine()) != null )
      {
        System.out.println( line );
      }
      
      int exitCode = process.waitFor();
            
      System.out.println( "ffprobe exit code " + exitCode );
      if ( exitCode != 0 )
        throw new IOException( "Unable to analyse video file. " + exitCode );

      System.out.println( new String( baos.toByteArray() ) );
      vi.doc = builder.parse( new ByteArrayInputStream( baos.toByteArray() ) );
            
      return vi;
    }
    catch ( ParserConfigurationException | SAXException ex )
    {
      Logger.getLogger( FfProbeRunner.class.getName() ).log( Level.SEVERE, null, ex );
      throw new IOException();
    }
  }
  
  public void test()
  {
    File vinf  = new File( config.getHomedir(), "media1-original.mp4" );
    try
    {
      runFfProbe( vinf );
    }
    catch ( IOException | InterruptedException ex )
    {
      Logger.getLogger(FfProbeRunner.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }
}
