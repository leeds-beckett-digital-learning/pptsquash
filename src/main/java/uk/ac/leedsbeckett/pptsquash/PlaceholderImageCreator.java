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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author maber01
 */
public class PlaceholderImageCreator
{
  public static boolean createPlaceholderImage( String format, File file, int w, int h )
  {
    int base = w;
    if ( h < base ) base = h;
    base = base / 3;
    
    int casew = base*2/3;
    int caseh = casew*2/3;
    int reelr = base*3/10;
    int crossw = base*6/8;
    
    try
    {
      BufferedImage image = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
      
      Graphics2D g = (Graphics2D) image.getGraphics();
      g.setColor( Color.white );
      g.fillRect( 0, 0, w, h );
      g.translate( w/2, h/2 );
      g.setColor( Color.black );
      
      g.fillRoundRect( -casew/2, -caseh/2, casew, caseh, base/6, base/6 );
      g.fillOval( -reelr, -caseh/2 -reelr/2, reelr, reelr );
      g.fillOval( 0,        -caseh/2 -reelr/2, reelr, reelr );
      
      Path2D.Float lens = new Path2D.Float();
      lens.moveTo( 2*casew/6, 0 );
      lens.lineTo( 2*casew/3, -caseh/2 );
      lens.lineTo( 2*casew/3,  caseh/2 );
      lens.closePath();
      g.fill( lens );
      
      g.setColor( Color.red );
      Stroke s = new BasicStroke( base/10 );
      g.setStroke( s );
      g.drawLine( -crossw/2, -crossw/2, crossw/2,  crossw/2 );
      g.drawLine( -crossw/2,  crossw/2, crossw/2, -crossw/2 );
      
      ImageIO.write( image, format, file );
      return true;
    }
    catch ( IOException ex )
    {
      Logger.getLogger( PlaceholderImageCreator.class.getName() ).log( Level.SEVERE, null, ex );
      return false;
    }
  }
  
  public static void main( String[] args )
  {
    File img = new File( System.getProperty("user.home"), "test.png" );
    createPlaceholderImage( "png", img, 200, 400 );
  }
}
