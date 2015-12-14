package com.example.yangxp5.rebackandroid.model;

import java.util.List;

/**
 * 歌单
 * Created by yangxp5 on 2015/12/11.
 */
public class SongList {

    private String name;    //歌单名
    private String founder; //创建人
    private int songNumber; //歌曲数量
    private List<MusicInfo> musicList;

    public SongList() {
    }

    public SongList(String name, String founder, int songNumber) {
        this.name = name;
        this.founder = founder;
        this.songNumber = songNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }

    public List<MusicInfo> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<MusicInfo> musicList) {
        this.musicList = musicList;
    }
}
