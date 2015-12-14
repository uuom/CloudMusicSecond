package com.example.yangxp5.rebackandroid.model;

import java.io.Serializable;

/**
 * Music Info Model
 * 歌曲信息
 * Created by uuom on 15-12-9.
 */
public class MusicInfo implements Serializable {

    private String title;
    private String artist;
    private String album;
    private String path;
    private String imgPath;

    public MusicInfo() {
    }

    public MusicInfo(String album, String artist, String path, String title, String imgPath) {
        this.album = album;
        this.artist = artist;
        this.path = path;
        this.title = title;
        this.imgPath = imgPath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
