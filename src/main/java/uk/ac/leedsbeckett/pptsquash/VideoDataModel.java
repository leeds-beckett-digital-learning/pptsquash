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
public class VideoDataModel extends javax.swing.table.DefaultTableModel
{
  Class[] types = new Class []
  {
    java.lang.String.class, java.lang.String.class, java.lang.String.class, ProgressDatum.class
  };

  public VideoDataModel()
  {
    super( null, new String [] { "Embedded Media", "Original Size", "New Size", "Progress" } );
  }

  public void resetProcessingData()
  {
    for ( int i=0; i<this.getRowCount(); i++ )
    {
      setValueAt( null, i, 2 );
      setValueAt( null, i, 3 );
    }
  }
  
  @Override
  public Class getColumnClass(int columnIndex)
  {
    return types [columnIndex];
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return false;
  }
}
