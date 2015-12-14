package com.example.yangxp5.rebackandroid.view.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.MusicInfo;

public class MusicButtomFragment extends Fragment {

    public MusicButtomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_buttom, container, false);



        return view;
    }

}
