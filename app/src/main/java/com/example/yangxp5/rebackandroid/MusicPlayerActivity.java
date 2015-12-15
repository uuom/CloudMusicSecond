package com.example.yangxp5.rebackandroid;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.LinearInterpolator;

import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.service.MusicPlayerService;

import de.hdodenhof.circleimageview.CircleImageView;


public class MusicPlayerActivity extends ActionBarActivity {

    private Toolbar tb_toolbar;
    private CircleImageView civ_musicImage;

    private ObjectAnimator animation;
    private MusicPlayerService musicPlayerService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicPlayerService = ((MusicPlayerService.ServiceBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        
        initView();

        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        MusicInfo currentMusic = musicPlayerService.getCurrentMusic();

        if (currentMusic !=null){
            getSupportActionBar().setTitle(currentMusic.getTitle() + " - " + currentMusic.getArtist());
        }

        animation = ObjectAnimator.ofFloat(civ_musicImage, "Rotation", 0, 360);
        animation.setInterpolator(new LinearInterpolator());    //匀速
        animation.setRepeatCount(-1);   //循环动画
        animation.setDuration(18000);   //动画运行时间，可以控制旋转速率
        animation.start();
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        civ_musicImage = (CircleImageView) findViewById(R.id.civ_musicImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
