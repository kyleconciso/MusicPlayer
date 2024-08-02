/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.group9.musicplayer;

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
            }
        });
    }
}
