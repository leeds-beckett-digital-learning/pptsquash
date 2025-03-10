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

import java.text.DecimalFormat;

/**
 *
 * @author maber01
 */
public class DataSizeFormatter
{
  private static DecimalFormat df = new DecimalFormat( "#,##0.0" );
  
  public static String format( long l )
  {
    return df.format( (float)l / 1000000.0f ) + " MB";
  }
}
