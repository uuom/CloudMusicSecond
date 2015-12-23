package com.example.yangxp5.rebackandroid.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yangxp5.rebackandroid.model.MusicInfo;
import com.example.yangxp5.rebackandroid.view.fragment.MusicFragment;
import com.example.yangxp5.rebackandroid.view.fragment.TabFragment;

/**
 * Created by yangxp5 on 2015/12/11.
 */
public class LocalMusicTabAdapter extends FragmentPagerAdapter {

    private String[] tabNames;
    private Context context;

    public LocalMusicTabAdapter(FragmentManager fm ,String[] tabNames,Context context) {
        super(fm);
        this.tabNames = tabNames;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){

            Cursor mAudioCursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,// 字段　没有字段　就是查询所有信息　相当于SQL语句中的　“ * ”
                    null, // 查询条件
                    null, // 条件的对应?的参数
                    MediaStore.Audio.AudioColumns.TITLE);// 排序方式

            MusicInfo[] musicList = new MusicInfo[mAudioCursor.getCount()];

            // 循环输出歌曲的信息
            for (int i = 0; i < mAudioCursor.getCount(); i++) {
                mAudioCursor.moveToNext();
                // 找到歌曲标题和总时间对应的列索引
                int indexTitle = mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);//歌名
                int indexARTIST = mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);//艺术家
                int indexALBUM = mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);//专辑
                int indexPath = mAudioCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

                String strTitle = mAudioCursor.getString(indexTitle);
                String strARTIST = mAudioCursor.getString(indexARTIST);
                String strALBUM = mAudioCursor.getString(indexALBUM);
                String strPath = mAudioCursor.getString(indexPath);

                musicList[i] = new MusicInfo(strALBUM,strARTIST,strPath,strTitle);
            }

            return MusicFragment.newInstance(musicList);
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
