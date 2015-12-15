package com.example.yangxp5.rebackandroid;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yangxp5.rebackandroid.view.adapter.LocalMusicTabAdapter;


public class LocalMusicActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private PagerSlidingTabStrip psts_localMusic_tabs;
    private ViewPager vp_localMusic_pager;

    private LocalMusicTabAdapter localMusicTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        
        initView();

        //toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LocalMusicActivity.this,MainActivity.class);
                //intent.putExtra("tabNumber",1);
                //startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tabStrip and viewpager
        String[] tabNames = getResources().getStringArray(R.array.localMusic_tabNames);
        localMusicTabAdapter = new LocalMusicTabAdapter(getSupportFragmentManager(),tabNames,this);
        vp_localMusic_pager.setAdapter(localMusicTabAdapter);
        psts_localMusic_tabs.setViewPager(vp_localMusic_pager);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        psts_localMusic_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_localMusic_tabs);
        vp_localMusic_pager = (ViewPager) findViewById(R.id.vp_localMusic_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
