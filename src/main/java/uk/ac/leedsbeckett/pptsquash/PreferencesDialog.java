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
public class PreferencesDialog extends javax.swing.JDialog
{

  /**
   * Creates new form PreferencesDialog
   */
  public PreferencesDialog( java.awt.Frame parent, boolean modal )
  {
    super( parent, modal );
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jPanel2 = new javax.swing.JPanel();
    savePrefButton = new javax.swing.JButton();
    cancelPrefButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
    jPanel3.setLayout(new java.awt.BorderLayout());

    jLabel1.setText("Placeholder Text:");
    jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

    jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
    jPanel1.setLayout(new java.awt.BorderLayout());

    jTextArea1.setColumns(20);
    jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jTextArea1.setText("The video stream for this media has been removed.");
    jTextArea1.setWrapStyleWord(true);
    jScrollPane1.setViewportView(jTextArea1);

    jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    savePrefButton.setText("Save Preferences");
    jPanel2.add(savePrefButton);

    cancelPrefButton.setText("Cancel");
    jPanel2.add(cancelPrefButton);

    getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main( String args[] )
  {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try
    {
      for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() )
      {
        if ( "Nimbus".equals( info.getName() ) )
        {
          javax.swing.UIManager.setLookAndFeel( info.getClassName() );
          break;
        }
      }
    }
    catch ( ClassNotFoundException ex )
    {
      java.util.logging.Logger.getLogger( PreferencesDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
    }
    catch ( InstantiationException ex )
    {
      java.util.logging.Logger.getLogger( PreferencesDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
    }
    catch ( IllegalAccessException ex )
    {
      java.util.logging.Logger.getLogger( PreferencesDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
    }
    catch ( javax.swing.UnsupportedLookAndFeelException ex )
    {
      java.util.logging.Logger.getLogger( PreferencesDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
    }
    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        PreferencesDialog dialog = new PreferencesDialog( new javax.swing.JFrame(), true );
        dialog.addWindowListener( new java.awt.event.WindowAdapter()
        {
          @Override
          public void windowClosing( java.awt.event.WindowEvent e )
          {
            System.exit( 0 );
          }
        } );
        dialog.setVisible( true );
      }
    } );
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelPrefButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JButton savePrefButton;
  // End of variables declaration//GEN-END:variables
}
