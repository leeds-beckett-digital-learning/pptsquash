
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import uk.ac.leedsbeckett.pptsquash.Configuration;
import uk.ac.leedsbeckett.pptsquash.FfmpegRunner;
import uk.ac.leedsbeckett.pptsquash.PptVideoStripFrame;

/*
 * Copyright 2024 maber01.
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

/**
 *
 * @author maber01
 */
public class Main
{
  
  public static void main( String[] args )
  {
    try
    {
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
    }
    
    Configuration config = Configuration.prepHome();
    java.awt.EventQueue.invokeLater(() ->
    {
      PptVideoStripFrame frame = new PptVideoStripFrame( config );
      frame.setSize( 700, 400 );
      frame.setVisible( true );
    });
  }
}
