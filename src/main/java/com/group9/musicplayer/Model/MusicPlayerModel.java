/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group9.musicplayer.Model;

import com.group9.musicplayer.View.MusicPlayerView;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 *
 * @author psalm
 */

public class MusicPlayerModel extends PlaybackListener{
    //need reference to update gui in this class
    private MusicPlayerView view;
    
    //store song's details
    private Song currentSong;
    
    //use jlayer library to create and advanced player
    private AdvancedPlayer advancedPlayer;
    
    //pause boolean flag used to indicate whether the player has been paused
    private boolean isPaused;
    
    //stores in the last frame when the playback is finished (used for pausing and resuming)
    private int currentFrame;
    
    //track how many milliseconds has passed since playing the song
    private int currentTimeInMilli;
    
    //constructor
    public MusicPlayerModel(MusicPlayerView view){
        this.view = view;
        
    
    }
    
    public void loadSong(Song song){
        currentSong = song;
        
        //play the current song if not null
        if(currentSong != null){
            playCurrentSong();
        }
    }
    
    public void pauseSong(){
        if(advancedPlayer != null){
            //update isPaused flag
            isPaused = true;
            
            //then we want to stop the player
            stopSong();
        }
    }
    
    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }
    
    public void playCurrentSong(){
        if(currentSong == null)
            return;
        try{
            //read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            //create a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);
            
            //start music
            startMusicThread();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    if(isPaused){
                        //resume music from last frame
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    }
                    else{
                        //play music from teh beginning
                        advancedPlayer.play();
                    }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    //create a thread that will handle updating the slider
    private void startPlaybackSliderThread(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(!isPaused){
                    //increment current time milli
                    currentTimeInMilli++;
                    
                    //calculate into frame value
                    int calculatedFrame = (int)((double)currentTimeInMilli * currentSong.getFrameRatePerMilliseconds());
                    
                    //update GUI
                    view.setPlaybackSliderValue(calculatedFrame);
                    
                    //mimic 1 millisecond using thread.sleep
                    Thread.sleep(millis:1);
                    
                }
            }
        }).start();
    }
    

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        //this method gets called in the beginning of the song
        System.out.println("Playback Started");
    }
    
    @Override
    public void playbackFinished(PlaybackEvent evt) {
        //this method gets called when the song finishes or if the player gets closed
        System.out.println("Playback Finished");
        
        if(isPaused){
            currentFrame += (int) ((double)evt.getFrame() * currentSong.getFrameRatePerMilliseconds());

        }
    }
    
}
