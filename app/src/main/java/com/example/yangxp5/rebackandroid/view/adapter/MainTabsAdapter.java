package com.example.yangxp5.rebackandroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yangxp5.rebackandroid.view.fragment.MusicListFragment;
import com.example.yangxp5.rebackandroid.view.fragment.TabFragment;

/**
 * Created by yangxp5 on 2015/12/10.
 */
public class MainTabsAdapter extends FragmentPagerAdapter {

    private String[] tabNames;

    public MainTabsAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return new MusicListFragment();
        }
        return TabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }
}
