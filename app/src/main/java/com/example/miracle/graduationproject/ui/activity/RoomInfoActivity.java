package com.example.miracle.graduationproject.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.ui.fragment.TodayRoomFragment;
import com.example.miracle.graduationproject.ui.fragment.TomorrowRoomFragment;
import com.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/1/21 0021.
 */

public class RoomInfoActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    LinearLayout tvTitle;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.vp_room)
    ViewPager vpRoom;
    TodayRoomFragment todayRoomFragment = new TodayRoomFragment();
    TomorrowRoomFragment tomorrowRoomFragment = new TomorrowRoomFragment();
    Fragment[] fragmentList = {todayRoomFragment, tomorrowRoomFragment};
    String[] title = {"今天", "明天"};

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_room_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initViewPager();
        initTabLayout();
    }

    /**
     * 初始化 tabLayout
     */
    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("今天"));
        tabLayout.addTab(tabLayout.newTab().setText("明天"));
        tabLayout.setupWithViewPager(vpRoom);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        vpRoom.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList[position];
            }

            @Override
            public int getCount() {
                return fragmentList.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.iv_back, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_title:
                break;
        }
    }
}
