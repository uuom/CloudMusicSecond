package com.example.yangxp5.rebackandroid;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.service.MusicPlayerService;
import com.example.yangxp5.rebackandroid.utils.Contants;

import de.hdodenhof.circleimageview.CircleImageView;


public class MusicPlayerActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar tb_toolbar;
    private CircleImageView civ_musicImage;
    private ImageView iv_music_play;
    private ImageView iv_music_prev;
    private ImageView iv_music_next;
    private TextView tv_music_currentTime;
    private ProgressBar pb_music;
    private TextView tv_music_totalTime;

    private ObjectAnimator animation;
    private MusicPlayerService musicPlayerService;
    private MusicInfo currentMusic;
    private int currentPlayStatus;

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //获得Service
            musicPlayerService = ((MusicPlayerService.MusicPlayerServiceBinder)iBinder).getService();

            currentMusic = musicPlayerService.getCurrentMusic();
            if (currentMusic !=null){
                //设置标题
                getSupportActionBar().setTitle(currentMusic.getTitle() + " - " + currentMusic.getArtist());
            }
            currentPlayStatus = musicPlayerService.getCurrentPlayStatus();
            if (currentPlayStatus == 1){
                //设置旋转动画开关
                animation.start();
                iv_music_play.setImageResource(R.drawable.lock_btn_pause);
            }

            handler.post(run);
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
        initAnimation();

        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setEvent();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_CHANGE_MUSIC);
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_PLAY_MUSIC);
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_STOP_MUSIC);
        localBroadcastManager.registerReceiver(new MusicPlayerBroadcastReceiver(),filter);

        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void setEvent() {
        iv_music_play.setOnClickListener(this);
        iv_music_prev.setOnClickListener(this);
        iv_music_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_music_play:
                if (currentMusic != null){
                    if (musicPlayerService.getCurrentPlayStatus() == 0){
                        Intent intent = new Intent(this, MusicPlayerService.class);
                        intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_RESUME);
                        this.startService(intent);
                    }else{
                        Intent intent = new Intent(this, MusicPlayerService.class);
                        intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_PAUSE);
                        this.startService(intent);
                    }
                }
                break;
            case R.id.iv_music_prev:
                if (currentMusic != null){
                    Intent intent = new Intent(this, MusicPlayerService.class);
                    intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_PREV);
                    this.startService(intent);
                }
                break;
            case R.id.iv_music_next:
                if (currentMusic != null){
                    Intent intent = new Intent(this, MusicPlayerService.class);
                    intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_NEXT);
                    this.startService(intent);
                }
                break;
        }
    }

    private void initAnimation() {
        animation = ObjectAnimator.ofFloat(civ_musicImage, "Rotation", 0, 360);
        animation.setInterpolator(new LinearInterpolator());    //匀速
        animation.setRepeatCount(-1);   //循环动画
        animation.setDuration(18000);   //动画运行时间，可以控制旋转速率
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        civ_musicImage = (CircleImageView) findViewById(R.id.civ_musicImage);
        iv_music_play = (ImageView) findViewById(R.id.iv_music_play);
        iv_music_prev = (ImageView) findViewById(R.id.iv_music_prev);
        iv_music_next = (ImageView) findViewById(R.id.iv_music_next);
        tv_music_currentTime = (TextView) findViewById(R.id.tv_music_currentTime);
        pb_music = (ProgressBar) findViewById(R.id.pb_music);
        tv_music_totalTime = (TextView) findViewById(R.id.tv_music_totalTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            int totalTime = musicPlayerService.getmMediaPlayer().getDuration();
            int currentTime = musicPlayerService.getmMediaPlayer().getCurrentPosition();

            pb_music.setMax(totalTime);
            pb_music.setProgress(currentTime);

            tv_music_totalTime.setText(millionTimeToMunites(totalTime));
            tv_music_currentTime.setText(millionTimeToMunites(currentTime));

            handler.postDelayed(this,1000);
        }
    };

    private String millionTimeToMunites(int duration){
        int mTemp = duration/60000;
        int sTemp = duration%60000;

        sTemp = sTemp/1000;

        return(mTemp>=10?mTemp:"0"+mTemp) +":"+(sTemp>=10?sTemp:"0"+sTemp);
    }

    class MusicPlayerBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Contants.MUSIC_BUTTOM_ACTION_CHANGE_MUSIC:
                    MusicInfo musicInfo = intent.getExtras().getParcelable(Contants.EXTRA_KEY_SINGLE_MUSICINFO);
                    if (musicInfo !=null){
                        //设置标题
                        getSupportActionBar().setTitle(musicInfo.getTitle() + " - " + musicInfo.getArtist());
                    }
                    iv_music_play.setImageResource(R.drawable.lock_btn_pause);
                    animation.cancel();
                    animation.start();
                    break;
                case Contants.MUSIC_BUTTOM_ACTION_PLAY_MUSIC:
                    iv_music_play.setImageResource(R.drawable.lock_btn_pause);
                    animation.resume();
                    break;
                case Contants.MUSIC_BUTTOM_ACTION_STOP_MUSIC:
                    iv_music_play.setImageResource(R.drawable.lock_btn_play);
                    animation.pause();
                    break;
            }
        }
    }

}
