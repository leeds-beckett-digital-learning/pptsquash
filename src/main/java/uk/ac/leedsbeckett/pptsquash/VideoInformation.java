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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author maber01
 */
public class VideoInformation
{
  Document doc;
  boolean parsed = false;
  Element videostream = null;
  Integer width;
  Integer height;
  Float durationSecs;
  
  private void parse()
  {
    try    
    {
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression findVideo = xPath.compile( "/ffprobe/streams/stream[@codec_type = 'video']" );
      XPathExpression findHeight = xPath.compile( "@height" );
      XPathExpression findWidth = xPath.compile( "@width" );
      XPathExpression findDuration = xPath.compile( "@duration" );
      
      NodeList nodeList = (NodeList) findVideo.evaluate( doc, XPathConstants.NODESET );
      if ( nodeList.getLength() > 0 )
      {
        videostream = (Element)nodeList.item( 0 );
        String a;
        a = (String) findWidth.evaluate( videostream, XPathConstants.STRING );
        width = Integer.valueOf( a );
        a = (String) findHeight.evaluate( videostream, XPathConstants.STRING );
        height = Integer.valueOf( a );
        a = (String) findDuration.evaluate( videostream, XPathConstants.STRING );
        durationSecs = Float.valueOf( a );
      }
    }
    catch ( XPathExpressionException ex )
    {
      Logger.getLogger( VideoInformation.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }
  
  public Integer getWidth()
  {
    if ( !parsed ) parse();
    return width;
  }

  public Integer getHeight()
  {
    if ( !parsed ) parse();
    return height;
  }

  public Float getDurationSecs()
  {
    if ( !parsed ) parse();
    return durationSecs;
  }

  public boolean hasEnoughInformation()
  {
    if ( !parsed ) parse();
    return width!=null && height!=null && durationSecs!=null;
  }
}
