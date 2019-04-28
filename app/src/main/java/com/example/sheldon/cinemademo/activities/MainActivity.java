package com.example.sheldon.cinemademo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sheldon.cinemademo.NoSlidingViewPaper;
import com.example.sheldon.cinemademo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoSlidingViewPaper mViewPager;

    //底部菜單欄各個菜單項的點擊事件處理
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home://Home
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_film://Film
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_mine://Mine
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*初始化顯示內容*/
        mViewPager = findViewById(R.id.vp_main_container);
        final ArrayList<Fragment> fgLists = new ArrayList<>(3);
        fgLists.add(new HomeFragment());
        fgLists.add(new FilmFragment());
        fgLists.add(new MineFragment());
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2); //預加載剩下兩頁

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        /*給底部導航欄菜單項添加點擊事件*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //login success and jump to minefragment
        int jump_id = getIntent().getIntExtra("jump_id", 0);
        if(jump_id == 3) {
            mViewPager.setCurrentItem(2);
            navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());
        }
        if(jump_id == 2) {
            mViewPager.setCurrentItem(1);
            navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());
        }

    }
}


