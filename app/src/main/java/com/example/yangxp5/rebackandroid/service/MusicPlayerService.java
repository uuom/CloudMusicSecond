package com.example.yangxp5.rebackandroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Parcelable;

import com.example.yangxp5.rebackandroid.model.MusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by yangxp5 on 2015/12/14.
 */
public class MusicPlayerService extends Service {

    public static final String ARGS_MUSIC_LIST_KEY = "musicList";
    public static final String ARGS_MUSIC_CONTROL_KEY = "args_music_control_key";
    public static final String ARGS_MUSIC_CURRENT_POSITION_KEY = "current_position";
    public static final int FLAG_CONTROL_PLAY = 1;
    public static final int FLAG_CONTROL_PAUSE = 2;

    private Parcelable[] dataList;
    private int currentMusicPosition;
    private int currentPlayStatus = 0; //0:暂停 1：正在播放

    private static MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int flag = intent.getIntExtra(ARGS_MUSIC_CONTROL_KEY,FLAG_CONTROL_PLAY);
        switch (flag){
            case FLAG_CONTROL_PLAY :
                dataList = intent.getExtras().getParcelableArray(ARGS_MUSIC_LIST_KEY);
                currentMusicPosition = intent.getIntExtra(ARGS_MUSIC_CURRENT_POSITION_KEY,0);
                try {
                    MusicInfo musicInfo = (MusicInfo) dataList[currentMusicPosition];
                    try {
                        System.out.println(musicInfo.getPath());
                        mMediaPlayer.setDataSource(musicInfo.getPath());
                        mMediaPlayer.prepareAsync();
                        mMediaPlayer.start();
                        currentPlayStatus = 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    throw new RuntimeException("音乐播放出错了",e);
                }
                break;
            case FLAG_CONTROL_PAUSE :
                mMediaPlayer.pause();
                currentPlayStatus = 0;
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
