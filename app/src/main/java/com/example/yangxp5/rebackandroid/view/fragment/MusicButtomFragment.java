package com.example.yangxp5.rebackandroid.view.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangxp5.rebackandroid.MusicPlayerActivity;
import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.service.MusicPlayerService;
import com.example.yangxp5.rebackandroid.utils.Contants;

import de.greenrobot.event.EventBus;

public class MusicButtomFragment extends Fragment {

    private ImageView iv_musicImg;
    private TextView tv_title;
    private TextView tv_artist;
    private ImageView iv_play;
    private ImageView iv_next;
    private LinearLayout ly_music;

    private int currentStatus = 0;

    public MusicButtomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_buttom, container, false);

        initView(view);

        //注册BroadcastReceiver
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_CHANGE_MUSIC);
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_STOP_MUSIC);
        filter.addAction(Contants.MUSIC_BUTTOM_ACTION_PLAY_MUSIC);
        localBroadcastManager.registerReceiver(new MusicButtomBroadcastReceiver(), filter);

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStatus == 1) {
                    iv_play.setImageResource(R.drawable.playbar_btn_play);
                    currentStatus = 0;
                    Intent intent = new Intent(getActivity(), MusicPlayerService.class);
                    intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_PAUSE);
                    getActivity().startService(intent);
                } else {
                    iv_play.setImageResource(R.drawable.playbar_btn_pause);
                    currentStatus = 1;
                    Intent intent = new Intent(getActivity(), MusicPlayerService.class);
                    intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_RESUME);
                    getActivity().startService(intent);
                }
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicPlayerService.class);
                intent.putExtra(MusicPlayerService.ARGS_MUSIC_CONTROL_KEY, MusicPlayerService.FLAG_CONTROL_NEXT);
                getActivity().startService(intent);
            }
        });

        ly_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initView(View view) {
        iv_musicImg = (ImageView) view.findViewById(R.id.iv_musicImg);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_artist = (TextView) view.findViewById(R.id.tv_artist);
        iv_play = (ImageView) view.findViewById(R.id.iv_play);
        iv_next = (ImageView) view.findViewById(R.id.next);
        ly_music = (LinearLayout) view.findViewById(R.id.ly_music);
    }

    public void onEvent(MusicInfo event){
        System.out.println(">>>>>>>>>>>>" + event.getTitle());
        tv_title.setText(event.getTitle());
        iv_play.setImageResource(R.drawable.playbar_btn_pause);
        tv_artist.setText(event.getArtist());
        currentStatus = 1;
    }


    class MusicButtomBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Contants.MUSIC_BUTTOM_ACTION_CHANGE_MUSIC.equals(action)){
//                MusicInfo musicInfo = intent.getExtras().getParcelable(Contants.EXTRA_KEY_SINGLE_MUSICINFO);
//                tv_title.setText(musicInfo.getTitle());
//                iv_play.setImageResource(R.drawable.playbar_btn_pause);
//                tv_artist.setText(musicInfo.getArtist());
//                currentStatus = 1;
            }else if (Contants.MUSIC_BUTTOM_ACTION_STOP_MUSIC.equals(action)){
                iv_play.setImageResource(R.drawable.playbar_btn_play);
                currentStatus = 0;
            }else if(Contants.MUSIC_BUTTOM_ACTION_PLAY_MUSIC.equals(action)){
                iv_play.setImageResource(R.drawable.playbar_btn_pause);
                currentStatus = 1;
            }
        }
    }

}
