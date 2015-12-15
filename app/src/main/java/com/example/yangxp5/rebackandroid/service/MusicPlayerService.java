package com.example.yangxp5.rebackandroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.utils.Contants;

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
    public static final int FLAG_CONTROL_RESUME = 3;
    public static final int FLAG_CONTROL_NEXT = 4;
    public static final int FLAG_CONTROL_PRE = 4;

    private Parcelable[] dataList;
    private int currentMusicPosition;
    private int currentPlayStatus = 0; //0:暂停 1：正在播放
    private int currentMediaPosition;

    private MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                MusicInfo musicInfo = (MusicInfo) dataList[nextPosition()];
                try{
                    Thread.sleep(2000);
                    controllerMusicForNext(musicInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int flag = intent.getIntExtra(ARGS_MUSIC_CONTROL_KEY,FLAG_CONTROL_PLAY);
        switch (flag){
            case FLAG_CONTROL_PLAY :    //从列表点击播放
                dataList = intent.getExtras().getParcelableArray(ARGS_MUSIC_LIST_KEY);
                currentMusicPosition = intent.getIntExtra(ARGS_MUSIC_CURRENT_POSITION_KEY,0);
                try {
                    MusicInfo musicInfo = (MusicInfo) dataList[currentMusicPosition];
                    try {
                        controllerMusicForNext(musicInfo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    throw new RuntimeException("音乐播放出错了",e);
                }
                break;
            case FLAG_CONTROL_RESUME :  //暂停恢复
                mMediaPlayer.start();
                currentPlayStatus = 1;

                Intent playBroadcastIntent = new Intent();
                playBroadcastIntent.setAction(Contants.MUSIC_BUTTOM_ACTION_PLAY_MUSIC);
                LocalBroadcastManager.getInstance(this).sendBroadcast(playBroadcastIntent);
                break;
            case FLAG_CONTROL_PAUSE :   //暂停
                mMediaPlayer.pause();
                currentPlayStatus = 0;

                Intent stopBroadcastIntent = new Intent();
                stopBroadcastIntent.setAction(Contants.MUSIC_BUTTOM_ACTION_STOP_MUSIC);
                LocalBroadcastManager.getInstance(this).sendBroadcast(stopBroadcastIntent);
                break;
            case FLAG_CONTROL_NEXT:   //下一首
                int position = nextPosition();
                MusicInfo musicInfo = (MusicInfo) dataList[position];
                try{
                    controllerMusicForNext(musicInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private int nextPosition(){
        if (currentMusicPosition == dataList.length-1){
            currentMusicPosition = 0;
        }else{
            currentMusicPosition ++;
        }
        return currentMusicPosition;
    }

    //播放下一首
    private void controllerMusicForNext(MusicInfo musicInfo) throws IOException {
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(musicInfo.getPath());
        mMediaPlayer.prepare();
        mMediaPlayer.start();
        currentPlayStatus = 1;

        Intent broadcastIntent1 = new Intent();
        broadcastIntent1.setAction(Contants.MUSIC_BUTTOM_ACTION_CHANGE_MUSIC);
        broadcastIntent1.putExtra(Contants.EXTRA_KEY_SINGLE_MUSICINFO, musicInfo);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent1);
    }


    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = new ServiceBinder();
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null){
            mMediaPlayer.release();
        }
    }

    public class ServiceBinder extends Binder{
        public MusicPlayerService getService(){
            return MusicPlayerService.this;
        }
    }

    //getter
    public Parcelable[] getDataList() {
        return dataList;
    }

    public int getCurrentMusicPosition() {
        return currentMusicPosition;
    }

    public int getCurrentPlayStatus() {
        return currentPlayStatus;
    }

    public int getCurrentMediaPosition() {
        return currentMediaPosition;
    }

    public MusicInfo getCurrentMusic(){
        if (dataList != null && dataList.length>0){
            return (MusicInfo) dataList[currentMusicPosition];
        }
        return null;
    }
}
