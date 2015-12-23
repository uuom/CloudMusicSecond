package com.example.yangxp5.rebackandroid;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yangxp5.rebackandroid.view.adapter.LocalMusicTabAdapter;
import com.yalantis.starwars.TilesFrameLayout;
import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;

import java.lang.reflect.Field;


public class LocalMusicActivity extends ActionBarActivity implements TilesFrameLayoutListener {

    private Toolbar mToolbar;
    private PagerSlidingTabStrip psts_localMusic_tabs;
    private ViewPager vp_localMusic_pager;

    private LocalMusicTabAdapter localMusicTabAdapter;
    private TilesFrameLayout tilesFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        
        initView();

        //toolbar
        setSupportActionBar(mToolbar);
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

        tilesFrameLayout = (TilesFrameLayout) findViewById(R.id.tiles_frame_layout);
        tilesFrameLayout.setOnAnimationFinishedListener(this);

        setTranslucentStatus();
    }

    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        LinearLayout linear_bar = (LinearLayout) findViewById(R.id.linear_bar);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
        params.height = getStatusBarHeight();
        linear_bar.setLayoutParams(params);
    }

    public int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                tilesFrameLayout.startAnimation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationFinished() {
        onBackPressed();
    }
}
