/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group9.musicplayer.Model;

import javax.persistence.*;


@Entity
@Table(name = "Song")
public class Song {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="title")
    private String title;
    @Column(name="artist")
    private String artist;
    @Column(name="album")
    private String album;
    @Column(name="year")
    private int year;
    @Column(name="gebre")
    private String genre;
    @Column(name="filePath")
    private String filePath;
    @Column(name="coverFilePath")
    private String coverFilePath;

    public Song(String title, String artist, String album, int year, String genre, String filePath, String coverFilePath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
        this.coverFilePath = coverFilePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCoverFilePath() {
        return coverFilePath;
    }

    public void setCoverFilePath(String coverFilePath) {
        this.coverFilePath = coverFilePath;
    }
    
    
}
