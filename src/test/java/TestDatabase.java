/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author psalm
 */
import com.group9.musicplayer.Model.*;

public class TestDatabase {
    public static void main(String args[]) {
        SongRepository songRepository = new SongRepository();
        Song song = new Song("skibidi","bruno mars","the album of the year",2020,"d","e","f");
        songRepository.persist(song);
        
        for (Song v : songRepository.list()) {
            System.out.println(v.getTitle());
        }
    }
}
