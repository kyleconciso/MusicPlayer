/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.group9.musicplayer;

import com.group9.musicplayer.Controller.MusicPlayerController;
import com.group9.musicplayer.Model.MusicPlayerModel;
import com.group9.musicplayer.Model.Song;
import com.group9.musicplayer.View.MusicPlayerView;

/**
 *
 * @author psalm
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Develop");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicPlayerView().setVisible(true);
//                MusicPlayerController controller = new MusicPlayerController(view);
//                Song song = new Song("src/main/java/Assets/AJR - Bang.mp3");
//                System.out.println("Song Title: " + song.getSongTitle());
//                System.out.println("Song Artist: " + song.getSongArtist());
            }
        });
    }
}
