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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maber01
 */
public class Streamer implements Runnable
{
  private final InputStream input;
  private final OutputStream output;

  public Streamer( InputStream input, OutputStream output )
  {
    this.input = input;
    this.output = output;
  }
  
  @Override
  public void run()
  {
    try
    {
      input.transferTo( output );
    }
    catch ( IOException ex )
    {
      Logger.getLogger( Streamer.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }
  
}
