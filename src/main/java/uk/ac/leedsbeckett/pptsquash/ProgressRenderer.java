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

import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author maber01
 */
class ProgressRenderer implements TableCellRenderer
{
  private final JProgressBar progressBar;
  public ProgressRenderer( int min, int max )
  {
    progressBar = new JProgressBar( min, max );
    progressBar.setStringPainted( true );
  }

  @Override
  public Component getTableCellRendererComponent( JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column )
  {
    if ( value == null )
    {
      progressBar.setValue( 0 );
      progressBar.setString( "" );
    }
    else
    {
      ProgressDatum datum = (ProgressDatum)value;
      progressBar.setValue( datum.getPercent() );
      progressBar.setString( datum.getLabel() );
    }
    return progressBar;
  }
}
