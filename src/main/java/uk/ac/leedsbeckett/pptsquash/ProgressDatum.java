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

/**
 *
 * @author maber01
 */
public class ProgressDatum
{
  private final Integer percent;
  private final String label;

  public ProgressDatum( Integer percent, String label )
  {
    this.percent = percent;
    this.label = label;
  }

  public Integer getPercent()
  {
    return percent;
  }

  public String getLabel()
  {
    return label;
  }
}
