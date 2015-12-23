package com.example.yangxp5.rebackandroid;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yangxp5.rebackandroid.view.adapter.MainTabsAdapter;

import java.lang.reflect.Field;


public class MainActivity extends MusicBaseActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mSlidingTabStrip;

    private int defaultTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultTabNumber = getIntent().getIntExtra("tabNumber",0);

        initView();
        //APP Bar
        setSupportActionBar(mToolbar);

        //DrawerLayout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle =new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //PagerSlidingTabStrip
        MainTabsAdapter tabsAdapter = new MainTabsAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.tabs_names));
        mViewPager.setAdapter(tabsAdapter);
        mViewPager.setCurrentItem(defaultTabNumber);
        mSlidingTabStrip.setViewPager(mViewPager);
        mSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
