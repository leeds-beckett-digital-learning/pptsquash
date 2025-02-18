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
  Element audiostream = null;
  Element videostream = null;
  Integer audioIndex;
  Integer videoIndex;
  Integer width;
  Integer height;
  Float videoDurationSecs;
  Float audioDurationSecs;
  
  private void parse()
  {
    try    
    {
      NodeList nodeList;
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression findStream    = xPath.compile( "/ffprobe/streams/stream" );
      XPathExpression findIndex     = xPath.compile( "@index" );
      XPathExpression findCodecType = xPath.compile( "@codec_type" );
      XPathExpression findHeight    = xPath.compile( "@height" );
      XPathExpression findWidth     = xPath.compile( "@width" );
      XPathExpression findDuration  = xPath.compile( "@duration" );
      
      nodeList = (NodeList) findStream.evaluate( doc, XPathConstants.NODESET );
      for ( int i=0; i<nodeList.getLength(); i++ )
      {
        String a, at;
        Element stream = (Element)nodeList.item( i );
        at = (String) findCodecType.evaluate( stream, XPathConstants.STRING );
        if ( videostream == null && "video".equals( at ) )
        {
          videostream = stream;
          a = (String) findIndex.evaluate( stream, XPathConstants.STRING );
          videoIndex = Integer.valueOf( a );
          a = (String) findWidth.evaluate( stream, XPathConstants.STRING );
          width = Integer.valueOf( a );
          a = (String) findHeight.evaluate( stream, XPathConstants.STRING );
          height = Integer.valueOf( a );
          a = (String) findDuration.evaluate( stream, XPathConstants.STRING );
          videoDurationSecs = Float.valueOf( a );
        }
        if ( audiostream == null && "audio".equals( at ) )
        {
          audiostream = stream;
          a = (String) findIndex.evaluate( stream, XPathConstants.STRING );
          audioIndex = Integer.valueOf( a );
          a = (String) findDuration.evaluate( stream, XPathConstants.STRING );
          audioDurationSecs = Float.valueOf( a );
        }
      }
    }
    catch ( XPathExpressionException ex )
    {
      Logger.getLogger( VideoInformation.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

  public Integer getAudioIndex()
  {
    if ( !parsed ) parse();
    return audioIndex;
  }

  public Integer getVideoIndex()
  {
    if ( !parsed ) parse();
    return videoIndex;
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

  public Float getVideoDurationSecs()
  {
    if ( !parsed ) parse();
    return videoDurationSecs;
  }

  public Float getAudioDurationSecs()
  {
    if ( !parsed ) parse();
    return audioDurationSecs;
  }

  public boolean hasEnoughVideoInformation()
  {
    if ( !parsed ) parse();
    return width!=null && height!=null && videoDurationSecs!=null;
  }
}
