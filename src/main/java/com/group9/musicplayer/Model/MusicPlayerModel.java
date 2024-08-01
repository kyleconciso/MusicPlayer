/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group9.musicplayer.Model;

import com.group9.musicplayer.Model.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
/**
 *
 * @author psalm
 */
public class MusicPlayerModel {
    // PCS
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addPropertChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    // pcs.firePropertyChange("update", null, etc);
    
    // Class
    private SongRepository songRepository;
    private LinkedList<Song> songs;
    private boolean playing;
    private boolean lyricsOpen;
    private float playbackTime;
    private Song currentSong;
    private float volume;
    
    public LinkedList<Song> refreshSongs() {
        pcs.firePropertyChange("songs", null, songs);
        return songs;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        pcs.firePropertyChange("playing", null, playing);
        this.playing = playing;
    }

    public boolean isLyricsOpen() {
        return lyricsOpen;
    }

    public void setLyricsOpen(boolean lyricsOpen) {
        pcs.firePropertyChange("lyricsOpen", null, lyricsOpen);
        this.lyricsOpen = lyricsOpen;
    }

    public float getPlaybackTime() {
        return playbackTime;
    }

    public void setPlaybackTime(float playbackTime) {
        pcs.firePropertyChange("playbackTime", null, playbackTime);
        this.playbackTime = playbackTime;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        pcs.firePropertyChange("currentSong", null, currentSong);
        this.currentSong = currentSong;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        pcs.firePropertyChange("volume", null, volume);
        this.volume = volume;
    }
}
