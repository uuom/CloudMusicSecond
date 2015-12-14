package com.example.yangxp5.rebackandroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yangxp5.rebackandroid.view.fragment.MusicFragment;
import com.example.yangxp5.rebackandroid.view.fragment.TabFragment;

/**
 * Created by yangxp5 on 2015/12/11.
 */
public class LocalMusicTabAdapter extends FragmentPagerAdapter {

    private String[] tabNames;

    public LocalMusicTabAdapter(FragmentManager fm ,String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new MusicFragment();
        }
        return new TabFragment();
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
