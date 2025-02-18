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

import java.awt.CardLayout;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author maber01
 */
public class PptVideoStripFrame extends javax.swing.JFrame implements AnalyserListener
{
  private final PowerPointProcesssor processor;
  FileNameExtensionFilter filter = new FileNameExtensionFilter( "MS PowerPoint Files", "pptx" );
  Configuration config;
  int videocurrent;
  int videocount;
  VideoDataModel videoModel = new VideoDataModel();

  /**
   * Creates new form PptVideoStripFrame
   * @param config
   */
  public PptVideoStripFrame( Configuration config )
  {
    this.processor = new PowerPointProcesssor( config, this );
    this.config = config;
    initComponents();
    URL iconURL = getClass().getResource("/icon.png");
    ImageIcon icon = new ImageIcon(iconURL);
    setIconImage(icon.getImage());    
    busyLabel.setIcon( null );
    dataTable.setModel( videoModel );
    dataTable.setDefaultRenderer( ProgressDatum.class, new ProgressRenderer(0, 10000) );
    audioModeComboBox.setSelectedIndex( 3 );
    videoModeComboBox.setSelectedIndex( 1 );
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

    sep1 = new javax.swing.JPopupMenu.Separator();
    prefMenuItem = new javax.swing.JMenuItem();
    mainPanel = new javax.swing.JPanel();
    waitingPanel = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jLabel6 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane();
    errorPanel = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    jTextPane3 = new javax.swing.JTextPane();
    dataPanel = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    audioModeComboBox = new javax.swing.JComboBox<>();
    jLabel4 = new javax.swing.JLabel();
    videoModeComboBox = new javax.swing.JComboBox<>();
    jLabel1 = new javax.swing.JLabel();
    openedSizeLabel = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    savedSizeLabel = new javax.swing.JLabel();
    jPanel4 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    dataTable = new javax.swing.JTable();
    jPanel3 = new javax.swing.JPanel();
    busyLabel = new javax.swing.JLabel();
    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    openMenuItem = new javax.swing.JMenuItem();
    saveMenuItem = new javax.swing.JMenuItem();
    sep2 = new javax.swing.JPopupMenu.Separator();
    exitMenuItem = new javax.swing.JMenuItem();

    prefMenuItem.setMnemonic('P');
    prefMenuItem.setText("Preferences...");
    prefMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        prefMenuItemActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("PPt Squasher");

    mainPanel.setLayout(new java.awt.CardLayout());

    waitingPanel.setBackground(new java.awt.Color(255, 255, 255));
    waitingPanel.setLayout(new java.awt.BorderLayout());

    jPanel5.setBackground(new java.awt.Color(255, 255, 255));
    jPanel5.setLayout(new java.awt.BorderLayout());

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon.png"))); // NOI18N
    jPanel5.add(jLabel6, java.awt.BorderLayout.CENTER);

    waitingPanel.add(jPanel5, java.awt.BorderLayout.WEST);

    jScrollPane1.setBorder(null);

    jTextPane1.setEditable(false);
    jTextPane1.setBackground(new java.awt.Color(255, 255, 255));
    jTextPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 40, 8, 40));
    jTextPane1.setText("About PPt Squash\n\nThis app creates a copy of a PowerPoint file by removing video and recompressing audio streams.\n\nPowerPoint files with video are often too big to attach to emails, upload to file sharing services etc. and are poorly accessible by people with visual impairments. So, this tool makes it easier to share a PowerPoint with certain people. Note - the video cannot be recovered from the squashed copy - so do not discard the original file.\n\nThis tool is packaged with FFmpeg subject to the Gnu Lesser General Public License version 2.1.  FFmpeg performs all of the hard work for this application.");
    jTextPane1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
    jTextPane1.setFocusable(false);
    jScrollPane1.setViewportView(jTextPane1);

    waitingPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    mainPanel.add(waitingPanel, "waiting");

    errorPanel.setBackground(new java.awt.Color(255, 255, 255));
    errorPanel.setMaximumSize(new java.awt.Dimension(500, 400));
    errorPanel.setLayout(new java.awt.BorderLayout());

    jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(64, 64, 64, 64));

    jTextPane3.setText("Something unexpected went wrong.");
    jScrollPane3.setViewportView(jTextPane3);

    errorPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

    mainPanel.add(errorPanel, "error");

    dataPanel.setLayout(new java.awt.BorderLayout());

    jPanel1.setBackground(new java.awt.Color(255, 255, 255));
    jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
    jPanel1.setLayout(new java.awt.GridLayout(4, 2, 10, 4));

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel3.setText("Audio Mode:");
    jPanel1.add(jLabel3);

    audioModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Leave Unprocessed", "Moderate Compression", "Agressive Compression", "Replace with silence" }));
    jPanel1.add(audioModeComboBox);

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel4.setText("Video Mode:");
    jPanel1.add(jLabel4);

    videoModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Recompress Agressively", "Replace with Placeholder Image" }));
    jPanel1.add(videoModeComboBox);

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel1.setText("Opened PPTX File Size:");
    jPanel1.add(jLabel1);
    jPanel1.add(openedSizeLabel);

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel2.setText("Saved PPTX File Size:");
    jPanel1.add(jLabel2);
    jPanel1.add(savedSizeLabel);

    dataPanel.add(jPanel1, java.awt.BorderLayout.NORTH);

    jPanel4.setBackground(new java.awt.Color(255, 255, 255));
    jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
    jPanel4.setLayout(new java.awt.BorderLayout());

    dataTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "Name", "Original Size", "Progress", "New Size"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
      };
      boolean[] canEdit = new boolean []
      {
        false, false, false, false
      };

      public Class getColumnClass(int columnIndex)
      {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    jScrollPane2.setViewportView(dataTable);

    jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    dataPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

    mainPanel.add(dataPanel, "data");

    getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));
    jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
    jPanel3.setLayout(new java.awt.BorderLayout());

    busyLabel.setBackground(new java.awt.Color(255, 255, 255));
    busyLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    busyLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    busyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/busy.gif"))); // NOI18N
    busyLabel.setText("Status:");
    busyLabel.setOpaque(true);
    jPanel3.add(busyLabel, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

    fileMenu.setMnemonic('f');
    fileMenu.setText("File");

    openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    openMenuItem.setMnemonic('o');
    openMenuItem.setText("Open...");
    openMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        openMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(openMenuItem);

    saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    saveMenuItem.setMnemonic('s');
    saveMenuItem.setText("Save As...");
    saveMenuItem.setEnabled(false);
    saveMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        saveMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(saveMenuItem);
    fileMenu.add(sep2);

    exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    exitMenuItem.setMnemonic('x');
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    setJMenuBar(menuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_openMenuItemActionPerformed
  {//GEN-HEADEREND:event_openMenuItemActionPerformed
    processor.reset();
    openedSizeLabel.setText( "" );
    JFileChooser chooser = new JFileChooser();
    chooser.addChoosableFileFilter( filter );
    chooser.setFileFilter( filter );
    int outcome = chooser.showOpenDialog( this );
    if ( outcome == JFileChooser.APPROVE_OPTION )
    {
      while ( videoModel.getRowCount() > 0 )
        videoModel.removeRow( videoModel.getRowCount() - 1 );
      System.out.println( chooser.getSelectedFile().getAbsolutePath() );
      openedSizeLabel.setText( DataSizeFormatter.format( chooser.getSelectedFile().length() ) );
      processor.analyse( chooser.getSelectedFile() );
    }
  }//GEN-LAST:event_openMenuItemActionPerformed

  private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveMenuItemActionPerformed
  {//GEN-HEADEREND:event_saveMenuItemActionPerformed
    File ofile = new File( processor.getInfile().getParentFile(), "Squashed_" + processor.getInfile().getName() );
    JFileChooser chooser = new JFileChooser();
    chooser.addChoosableFileFilter( filter );
    chooser.setFileFilter( filter );
    chooser.setSelectedFile( ofile );
    int outcome = chooser.showSaveDialog( this );
    if ( outcome == JFileChooser.APPROVE_OPTION )
    {
      ofile = chooser.getSelectedFile();
      if ( processor.getInfile().getAbsolutePath().equals( ofile.getAbsolutePath() ) )
      {
        JOptionPane.showMessageDialog( this.getContentPane(), "Sorry, cannot save to same file as input.");
        return;
      }
      
      if ( ofile.exists() )
      {
        int reply = JOptionPane.showConfirmDialog( 
                this.getContentPane(), 
                "The file already exists. Overwrite?", 
                "Confirmation",
                JOptionPane.YES_NO_OPTION
                );
        if ( reply == JOptionPane.NO_OPTION )
          return;
      }
      System.out.println( "Output to " + ofile.getAbsolutePath() );
      videoModel.resetProcessingData();
      savedSizeLabel.setText( "" );
      processor.process(
              processor.getInfile(), 
              ofile, 
              audioModeComboBox.getSelectedIndex(), 
              videoModeComboBox.getSelectedIndex() );
    }
  }//GEN-LAST:event_saveMenuItemActionPerformed

  private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exitMenuItemActionPerformed
  {//GEN-HEADEREND:event_exitMenuItemActionPerformed
    System.out.println( "Request to quit, kill ffmpeg." );
    processor.kill();
    System.exit( 0 );
  }//GEN-LAST:event_exitMenuItemActionPerformed

  private void prefMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_prefMenuItemActionPerformed
  {//GEN-HEADEREND:event_prefMenuItemActionPerformed
    
  }//GEN-LAST:event_prefMenuItemActionPerformed

  @Override
  public void processingProgress( int pertenthousand )
  {
    String label = Integer.toString( pertenthousand / 100 ) + "." + (pertenthousand%100)/10 + "%";
    if ( pertenthousand == 10000 ) label = "done";
    videoModel.setValueAt( new ProgressDatum( pertenthousand, label ), videocurrent, 3  );    
  }

  @Override
  public void processingProgressFile( int fileNo, long size )
  {
    videocurrent = fileNo;
    
    if ( size == 0 )
    {
      videoModel.setValueAt( "", fileNo, 2 );
    }
    else
      videoModel.setValueAt( DataSizeFormatter.format( size ), fileNo, 2 );
  }

  @Override
  public void analysisProgressFile( String name, long size )
  {
    videoModel.addRow( new Object[] { name, DataSizeFormatter.format( size ), null, null } );
  }

  @Override
  public void analyserStatusChange()
  {
    CardLayout cl = (CardLayout)mainPanel.getLayout();
    switch ( processor.getStatus() )
    {
      case PowerPointProcesssor.STATUS_WAITING:
        busyLabel.setIcon( null );
        cl.show( mainPanel, "waiting" );
        openMenuItem.setEnabled( true );
        saveMenuItem.setEnabled( false );
        break;
      case PowerPointProcesssor.STATUS_ANALYSING:
        cl.show( mainPanel, "data" );
        busyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/busy.gif"))); // NOI18N
        busyLabel.setText( "Analysing" );
        openMenuItem.setEnabled( false );
        saveMenuItem.setEnabled( false );
        break;
      case PowerPointProcesssor.STATUS_PROCESSING:
        cl.show( mainPanel, "data" );
        busyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/busy.gif"))); // NOI18N
        busyLabel.setText( "Processing" );
        openMenuItem.setEnabled( false );
        saveMenuItem.setEnabled( false );
        break;
      case PowerPointProcesssor.STATUS_ANALYSED:
        busyLabel.setIcon( null );
        busyLabel.setText( "Analysis Complete" );
        videocount = processor.getVideoCount();
        cl.show( mainPanel, "data" );
        openMenuItem.setEnabled( true );
        saveMenuItem.setEnabled(processor.getVideoBytes() > 0L );
        break;
      case PowerPointProcesssor.STATUS_PROCESSED:
        busyLabel.setIcon( null );
        busyLabel.setText( "Processing Complete" );
        cl.show( mainPanel, "data" );
        openMenuItem.setEnabled( true );
        saveMenuItem.setEnabled( true );
        long outlen = processor.getOutfile().length();
        savedSizeLabel.setText( DataSizeFormatter.format( outlen ) );
        break;
      case PowerPointProcesssor.STATUS_ERROR:
        busyLabel.setIcon( null );
        busyLabel.setText( "An error occured" );
        cl.show( mainPanel, "data" );
        openMenuItem.setEnabled( true );
        saveMenuItem.setEnabled( true );
        break;
      default:
        busyLabel.setIcon( null );
        openMenuItem.setEnabled( true );
        saveMenuItem.setEnabled( false );
        cl.show( mainPanel, "error" );
    }
  }
  

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> audioModeComboBox;
  private javax.swing.JLabel busyLabel;
  private javax.swing.JPanel dataPanel;
  private javax.swing.JTable dataTable;
  private javax.swing.JPanel errorPanel;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTextPane jTextPane1;
  private javax.swing.JTextPane jTextPane3;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JLabel openedSizeLabel;
  private javax.swing.JMenuItem prefMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  private javax.swing.JLabel savedSizeLabel;
  private javax.swing.JPopupMenu.Separator sep1;
  private javax.swing.JPopupMenu.Separator sep2;
  private javax.swing.JComboBox<String> videoModeComboBox;
  private javax.swing.JPanel waitingPanel;
  // End of variables declaration//GEN-END:variables



}
