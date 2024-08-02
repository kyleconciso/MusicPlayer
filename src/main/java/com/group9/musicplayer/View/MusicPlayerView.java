/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.group9.musicplayer.View;


import com.group9.musicplayer.Model.MusicPlayerModel;
import com.group9.musicplayer.Model.Song;
import com.group9.musicplayer.View.RoundedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author psalm
 */
public class MusicPlayerView extends javax.swing.JFrame implements PropertyChangeListener {
    
    // PCS
    public void propertyChange(PropertyChangeEvent evt) {
        
    }
    
    
    // Class
    private boolean isPlaying;
    
    public MusicPlayerView() {
        initComponents();
        
        LyricsArea.setBorder(new RoundedBorder(30));
        customizeButton(pauseandplayButton, "src/main/java/Assets/play.png");
        customizeButton(nextButton, "src/main/java/Assets/next.png");
        customizeButton(previousButton, "src/main/java/Assets/previous.png");
        customizeButton2(soundIcon, "src/main/java/Assets/volume.png");
        pauseandplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        soundIcon.setEnabled(false);
        songNameplace.setHorizontalAlignment(JTextField.CENTER);
        
        
        musicPlayer = new MusicPlayerModel(this);
        
        jFileChooser = new JFileChooser();
        
        //set a default path for file explorer
        jFileChooser.setCurrentDirectory(new File("src/main/java/Assets"));
        
        //filter file chooser to only see .mp3 files
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
        
        addGuiComponents();
    }
    
    
    private void addGuiComponents(){
        //add toolbar
        addToolbar();
    }
    
    private void customizeButton(JButton button, String iconPath) {
        button.setIcon(new ImageIcon(iconPath));
        button.setPreferredSize(new Dimension(50, 50));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    private void customizeButton2(JButton button, String iconPath) {
        button.setIcon(new ImageIcon(iconPath));
        button.setPreferredSize(new Dimension(30, 30));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    
    class jPanelGradient extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            
            Color color1 = new Color(59, 76, 92);
            Color color2 = new Color(153,204,255);
            GradientPaint gp = new GradientPaint(0,0,color1,0,height,color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
    
    private void addToolbar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0,0,getWidth(),20);
        
        //prevent toolbar from being moved
        toolBar.setFloatable(false);
        
        //add drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);
        
        //now we will add a song menu where we will place the loading song option
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);
        
        //add the "load song" item in the songMenu
        JMenuItem loadSong = new JMenuItem("Load Song");
        loadSong.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //an integer is returned to us to let us know what the user did
                int result = jFileChooser.showOpenDialog(MusicPlayerView.this);
                File selectedFile = jFileChooser.getSelectedFile();
                
                //this means that we are also checking if the user pressed the open button
                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){
                    //create a song obj based on selected file
                    Song song = new Song(selectedFile.getPath());
                    
                    //load song in music player
                    musicPlayer.loadSong(song);
                    
                    //update song title and artist
                    updateSongTitleAndArtist(song);
                    
                    //update duration slider
                    updatePlaybackSlider(song);
                    
                    //toggle on pause button and toggle off play button
                    enablePauseButtonDisplayPlayButton();
                    
                }
            }
        });
        songMenu.add(loadSong);
        
        //now we will add the playlist menu
        JMenu playlistMenu = new JMenu("Playlist");
        menuBar.add(playlistMenu);
        
        //then add teh items to the playlist menu
        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        playlistMenu.add(createPlaylist);
        
        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlistMenu.add(loadPlaylist);
        
        add(toolBar);
    
    }    
    
    //this will be used to update our slider from the music player class
    public void setPlaybackSliderValue(int frame){
        durationSlider.setValue(frame);
    }
    
    private void updateSongTitleAndArtist(Song song){
        songNameplace.setText(song.getSongTitle()+ " by "+ song.getSongArtist());
    }
    
    private void updatePlaybackSlider(Song song){
        durationSlider.setMaximum(song.getMp3File().getFrameCount());
        
        //create teh song length label
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        
        //beginning will be 00:00
        JLabel labelBeginning = new JLabel("00:00");
        labelBeginning.setFont(new Font("Dialog",Font.BOLD,18));
        
        //end will vary depending on the song
        JLabel labelEnd = new JLabel(song.getSongLength());
        labelEnd.setFont(new Font("Dialog",Font.BOLD,18));
        
        labelTable.put(0, labelBeginning);
        labelTable.put(song.getMp3File().getFrameCount(),labelEnd);
        
        durationSlider.setLabelTable(labelTable);
        durationSlider.setPaintLabels(true);
    }
    
    private void enablePauseButtonDisplayPlayButton(){
        //retrieve reference to play button from playbackBtns panel
        pauseandplayButton.setIcon(new ImageIcon("src/main/java/Assets/pause.png"));
    }
    
    private void enablePlayButtonDisplayPauseButton(){
        //retrieve reference to play button from playbackBtns panel
        pauseandplayButton.setIcon(new ImageIcon("src/main/java/Assets/play.png"));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        label3 = new java.awt.Label();
        label6 = new java.awt.Label();
        label1 = new java.awt.Label();
        jPanel1 = new jPanelGradient();
        jScrollPane1 = new javax.swing.JScrollPane();
        LyricsArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        songPanel = new javax.swing.JPanel();
        songInfoPanel = new javax.swing.JPanel();
        covertLabel = new javax.swing.JLabel();
        titleArtist = new javax.swing.JPanel();
        artistLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        albumLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        genreLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        songPanel1 = new javax.swing.JPanel();
        songInfoPanel1 = new javax.swing.JPanel();
        covertLabel1 = new javax.swing.JLabel();
        titleArtist1 = new javax.swing.JPanel();
        artistLabel1 = new javax.swing.JLabel();
        titleLabel1 = new javax.swing.JLabel();
        albumLabel1 = new javax.swing.JLabel();
        yearLabel1 = new javax.swing.JLabel();
        genreLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        songPanel2 = new javax.swing.JPanel();
        songInfoPanel2 = new javax.swing.JPanel();
        covertLabel2 = new javax.swing.JLabel();
        titleArtist2 = new javax.swing.JPanel();
        artistLabel2 = new javax.swing.JLabel();
        titleLabel2 = new javax.swing.JLabel();
        albumLabel2 = new javax.swing.JLabel();
        yearLabel2 = new javax.swing.JLabel();
        genreLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        btnsPanel = new javax.swing.JPanel();
        pauseandplayButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        durationSlider = new javax.swing.JSlider();
        soundIcon = new javax.swing.JButton();
        volumeSlider = new javax.swing.JSlider();
        songNameplace = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        label3.setText("label3");

        label6.setText("label6");

        label1.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 625));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));

        LyricsArea.setEditable(false);
        LyricsArea.setColumns(20);
        LyricsArea.setRows(5);
        jScrollPane1.setViewportView(LyricsArea);

        jPanel2.setBackground(new java.awt.Color(140, 181, 232));
        jPanel2.setPreferredSize(new java.awt.Dimension(650, 450));

        jPanel3.setPreferredSize(new java.awt.Dimension(605, 380));

        songPanel.setForeground(new java.awt.Color(204, 204, 204));
        songPanel.setMaximumSize(new java.awt.Dimension(2147483647, 32));
        songPanel.setMinimumSize(new java.awt.Dimension(222, 32));
        songPanel.setPreferredSize(new java.awt.Dimension(605, 32));
        songPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        songInfoPanel.setLayout(new java.awt.GridBagLayout());

        covertLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        covertLabel.setText("coverArt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel.add(covertLabel, gridBagConstraints);

        titleArtist.setLayout(new java.awt.BorderLayout());

        artistLabel.setText("artist");
        titleArtist.add(artistLabel, java.awt.BorderLayout.CENTER);

        titleLabel.setText("title");
        titleArtist.add(titleLabel, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        songInfoPanel.add(titleArtist, gridBagConstraints);

        albumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumLabel.setText("album");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel.add(albumLabel, gridBagConstraints);

        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel.setText("year");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel.add(yearLabel, gridBagConstraints);

        genreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        genreLabel.setText("genre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel.add(genreLabel, gridBagConstraints);

        songPanel.add(songInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        jButton1.setText("jButton1");
        songPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 30));

        jPanel3.add(songPanel);

        songPanel1.setForeground(new java.awt.Color(204, 204, 204));
        songPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 32));
        songPanel1.setMinimumSize(new java.awt.Dimension(222, 32));
        songPanel1.setPreferredSize(new java.awt.Dimension(605, 32));
        songPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        songInfoPanel1.setLayout(new java.awt.GridBagLayout());

        covertLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        covertLabel1.setText("coverArt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel1.add(covertLabel1, gridBagConstraints);

        titleArtist1.setLayout(new java.awt.BorderLayout());

        artistLabel1.setText("artist");
        titleArtist1.add(artistLabel1, java.awt.BorderLayout.CENTER);

        titleLabel1.setText("title");
        titleArtist1.add(titleLabel1, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        songInfoPanel1.add(titleArtist1, gridBagConstraints);

        albumLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumLabel1.setText("album");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel1.add(albumLabel1, gridBagConstraints);

        yearLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel1.setText("year");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel1.add(yearLabel1, gridBagConstraints);

        genreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        genreLabel1.setText("genre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel1.add(genreLabel1, gridBagConstraints);

        songPanel1.add(songInfoPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        jButton2.setText("jButton1");
        songPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 30));

        jPanel3.add(songPanel1);

        songPanel2.setForeground(new java.awt.Color(204, 204, 204));
        songPanel2.setMaximumSize(new java.awt.Dimension(2147483647, 32));
        songPanel2.setMinimumSize(new java.awt.Dimension(222, 32));
        songPanel2.setPreferredSize(new java.awt.Dimension(605, 32));
        songPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        songInfoPanel2.setLayout(new java.awt.GridBagLayout());

        covertLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        covertLabel2.setText("coverArt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel2.add(covertLabel2, gridBagConstraints);

        titleArtist2.setLayout(new java.awt.BorderLayout());

        artistLabel2.setText("artist");
        titleArtist2.add(artistLabel2, java.awt.BorderLayout.CENTER);

        titleLabel2.setText("title");
        titleArtist2.add(titleLabel2, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        songInfoPanel2.add(titleArtist2, gridBagConstraints);

        albumLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumLabel2.setText("album");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel2.add(albumLabel2, gridBagConstraints);

        yearLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel2.setText("year");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel2.add(yearLabel2, gridBagConstraints);

        genreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        genreLabel2.setText("genre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        songInfoPanel2.add(genreLabel2, gridBagConstraints);

        songPanel2.add(songInfoPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        jButton3.setText("jButton1");
        songPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 30));

        jPanel3.add(songPanel2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        btnsPanel.setBackground(new java.awt.Color(0, 0, 51));
        btnsPanel.setPreferredSize(new java.awt.Dimension(486, 70));

        pauseandplayButton.setPreferredSize(new java.awt.Dimension(50, 50));
        pauseandplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseandplayButtonActionPerformed(evt);
            }
        });

        nextButton.setPreferredSize(new java.awt.Dimension(50, 50));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.setPreferredSize(new java.awt.Dimension(50, 50));
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        soundIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        soundIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soundIconActionPerformed(evt);
            }
        });

        songNameplace.setEditable(false);

        javax.swing.GroupLayout btnsPanelLayout = new javax.swing.GroupLayout(btnsPanel);
        btnsPanel.setLayout(btnsPanelLayout);
        btnsPanelLayout.setHorizontalGroup(
            btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pauseandplayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnsPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(durationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(songNameplace, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)))
                .addComponent(soundIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        btnsPanelLayout.setVerticalGroup(
            btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsPanelLayout.createSequentialGroup()
                .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, btnsPanelLayout.createSequentialGroup()
                                .addComponent(durationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(songNameplace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pauseandplayButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(previousButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nextButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(btnsPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(soundIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pauseandplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseandplayButtonActionPerformed
        //toggle off pause button and toggle on play button
        if (isPlaying) {
                    // Pause the song
                    musicPlayer.pauseSong();
                    enablePlayButtonDisplayPauseButton();
                } 
        else {
                    // Play the song
                    musicPlayer.playCurrentSong();
                    enablePauseButtonDisplayPlayButton();
                }
                // Toggle the playing state
                isPlaying = !isPlaying;
            
        
//                if (isPlaying) {
//                    // Pause the media, update icon
//                    pauseandplayButton.setIcon(new ImageIcon("src/main/java/Assets/pause.png"));
//                } else {
//                    // Play the media, update icon
//                    pauseandplayButton.setIcon(new ImageIcon("src/main/java/Assets/play.png"));
//                }
//                // Toggle the playing state
//                isPlaying = !isPlaying;
//            

        // TODO add your handling code here:
    }//GEN-LAST:event_pauseandplayButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        
        nextButton.setIcon(new ImageIcon("src/main/java/Assets/next.png"));
    }//GEN-LAST:event_nextButtonActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        // TODO add your handling code here:
        
        previousButton.setIcon(new ImageIcon("src/main/java/Assets/previous.png"));
    }//GEN-LAST:event_previousButtonActionPerformed

    private void soundIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soundIconActionPerformed
        // TODO add your handling code here:
        
        soundIcon.setIcon(new ImageIcon("src/main/java/Assets/volume.png"));
        
    }//GEN-LAST:event_soundIconActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MusicPlayerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MusicPlayerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MusicPlayerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MusicPlayerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicPlayerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea LyricsArea;
    private javax.swing.JLabel albumLabel;
    private javax.swing.JLabel albumLabel1;
    private javax.swing.JLabel albumLabel2;
    private javax.swing.JLabel artistLabel;
    private javax.swing.JLabel artistLabel1;
    private javax.swing.JLabel artistLabel2;
    private javax.swing.JPanel btnsPanel;
    private javax.swing.JLabel covertLabel;
    private javax.swing.JLabel covertLabel1;
    private javax.swing.JLabel covertLabel2;
    private javax.swing.JSlider durationSlider;
    private javax.swing.JLabel genreLabel;
    private javax.swing.JLabel genreLabel1;
    private javax.swing.JLabel genreLabel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private java.awt.Label label3;
    private java.awt.Label label6;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton pauseandplayButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JPanel songInfoPanel;
    private javax.swing.JPanel songInfoPanel1;
    private javax.swing.JPanel songInfoPanel2;
    private javax.swing.JTextField songNameplace;
    private javax.swing.JPanel songPanel;
    private javax.swing.JPanel songPanel1;
    private javax.swing.JPanel songPanel2;
    private javax.swing.JButton soundIcon;
    private javax.swing.JPanel titleArtist;
    private javax.swing.JPanel titleArtist1;
    private javax.swing.JPanel titleArtist2;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel titleLabel1;
    private javax.swing.JLabel titleLabel2;
    private javax.swing.JSlider volumeSlider;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JLabel yearLabel1;
    private javax.swing.JLabel yearLabel2;
    // End of variables declaration//GEN-END:variables
}
