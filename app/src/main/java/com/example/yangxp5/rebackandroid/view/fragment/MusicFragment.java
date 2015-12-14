package com.example.yangxp5.rebackandroid.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yangxp5.rebackandroid.R;

/**
 * Created by yangxp5 on 2015/12/11.
 */
public class MusicFragment  extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music,container,false);
        return view;
    }
}
