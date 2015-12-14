package com.example.yangxp5.rebackandroid.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yangxp5.rebackandroid.LocalMusicActivity;
import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.SongList;
import com.example.yangxp5.rebackandroid.view.adapter.ExpanableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicListFragment extends Fragment implements View.OnClickListener {

    //view
    private ExpandableListView mExpandableListView;
    private ExpanableListViewAdapter listViewAdapter;
    private LinearLayout ll_localMusicLayout;
    private LinearLayout ll_historyMusicLayout;
    private LinearLayout ll_downloadManageLayout;

    //member
    private List<String> groupList;
    private Map<String,List<SongList>> dataList;

    public MusicListFragment() {
        // Required empty public constructor

        groupList = new ArrayList<>();
        groupList.add("创建的歌单");
        groupList.add("收藏的歌单");

        dataList = new HashMap<>();
        List<SongList> songlist1  = new ArrayList<>();
        songlist1.add(new SongList("我喜欢的音乐","yangxp5",49));
        songlist1.add(new SongList("抖腿","yangxp5",0));
        dataList.put("创建的歌单", songlist1);

        List<SongList> songlist2  = new ArrayList<>();
        songlist2.add(new SongList("【欧美】大神们用这样的方式演绎人生","yangxp5",49));
        songlist2.add(new SongList("999+ 评论，电音，不一样的精彩","yangxp5",0));
        dataList.put("收藏的歌单",songlist2);
    }

    // TODO: Rename and change types and number of parameters
    public static MusicListFragment newInstance() {
        MusicListFragment fragment = new MusicListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);

        initView(view);

        listViewAdapter = new ExpanableListViewAdapter(getActivity(),groupList,dataList);
        mExpandableListView.setAdapter(listViewAdapter);
        for (int i=0;i<listViewAdapter.getGroupCount();i++){
            mExpandableListView.expandGroup(i);
        }

        setEvent();

        return view;
    }

    private void setEvent() {
        ll_localMusicLayout.setOnClickListener(this);
        ll_historyMusicLayout.setOnClickListener(this);
        ll_downloadManageLayout.setOnClickListener(this);
    }

    private void initView(View view) {
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.elb_expandableListView);
        ll_localMusicLayout = (LinearLayout) view.findViewById(R.id.ll_localMusicLayout);
        ll_historyMusicLayout = (LinearLayout) view.findViewById(R.id.ll_historyMusicLayout);
        ll_downloadManageLayout = (LinearLayout) view.findViewById(R.id.ll_downloadManageLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_localMusicLayout:
                Intent intent = new Intent(this.getActivity(), LocalMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_historyMusicLayout:
                break;
            case R.id.ll_downloadManageLayout:
                break;
        }
    }
}
