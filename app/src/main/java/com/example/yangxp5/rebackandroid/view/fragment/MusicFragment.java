package com.example.yangxp5.rebackandroid.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.service.MusicPlayerService;
import com.example.yangxp5.rebackandroid.view.adapter.MusicListAdapter;
import com.example.yangxp5.rebackandroid.view.part.DividerItemDecoration;

/**
 * Created by yangxp5 on 2015/12/11.
 */
public class MusicFragment  extends Fragment {

    //view
    private RecyclerView rv_musicList;
    private TextView tv_musicTotal;
    private LinearLayout ll_allMusic;

    //remember
    private MusicListAdapter mMusicListAdapter;

    //data
    private MusicInfo[] dataList;

    public MusicFragment() {
    }

    public static MusicFragment newInstance(MusicInfo[] dataList){
        MusicFragment musicFragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArray("musicList",dataList);
        musicFragment.setArguments(args);
        return musicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = (MusicInfo[]) getArguments().getParcelableArray("musicList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music,container,false);

        initView(view);

        tv_musicTotal.setText("播放全部(共" + dataList.length + "首)");
        ll_allMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicPlayerService.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(MusicPlayerService.ARGS_MUSIC_LIST_KEY,dataList);
                intent.putExtra(MusicPlayerService.ARGS_MUSIC_CURRENT_POSITION_KEY,0);
                intent.putExtras(bundle);
                getActivity().startService(intent);
            }
        });

        mMusicListAdapter = new MusicListAdapter(dataList);
        mMusicListAdapter.setItemClickListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void onclick(View view, int position) {
                Intent intent = new Intent(getActivity(), MusicPlayerService.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArray(MusicPlayerService.ARGS_MUSIC_LIST_KEY,dataList);
                intent.putExtra(MusicPlayerService.ARGS_MUSIC_CURRENT_POSITION_KEY,position);
                intent.putExtras(bundle);
                getActivity().startService(intent);


            }
        });
        rv_musicList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_musicList.setAdapter(mMusicListAdapter);
        rv_musicList.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

    private void initView(View view) {
        rv_musicList = (RecyclerView) view.findViewById(R.id.rv_musicList);
        tv_musicTotal = (TextView) view.findViewById(R.id.tv_musicTotal);
        ll_allMusic = (LinearLayout) view.findViewById(R.id.ll_allMusic);
    }

}
