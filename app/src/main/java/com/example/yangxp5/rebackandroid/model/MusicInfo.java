package com.example.yangxp5.rebackandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Music Info Model
 * 歌曲信息
 * Created by uuom on 15-12-9.
 */
public class MusicInfo implements Parcelable {

    private String title;
    private String artist;
    private String album;
    private String path;
    private String imgPath;

    public MusicInfo() {
    }

    public MusicInfo(String album, String artist, String path, String title) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.album);
        dest.writeString(this.path);
        dest.writeString(this.imgPath);
    }

    protected MusicInfo(Parcel in) {
        this.title = in.readString();
        this.artist = in.readString();
        this.album = in.readString();
        this.path = in.readString();
        this.imgPath = in.readString();
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        public MusicInfo createFromParcel(Parcel source) {
            return new MusicInfo(source);
        }

        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
}
