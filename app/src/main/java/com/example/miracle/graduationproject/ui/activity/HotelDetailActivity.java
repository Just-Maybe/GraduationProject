package com.example.miracle.graduationproject.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.adapter.RoomAdapter;
import com.example.miracle.graduationproject.api.NetWorkRequest;
import com.example.miracle.graduationproject.dialog.RoomDetitalDialog;
import com.example.miracle.graduationproject.dto.Hotel;
import com.example.miracle.graduationproject.dto.Room;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class HotelDetailActivity extends BaseActivity {
    public static final String HOTEL_ID = "hotel_id";

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.vp_hotel)
    ViewPager vpHotel;
    @Bind(R.id.tv_room_address)
    TextView tvRoomAddress;
    @Bind(R.id.tv_room_introduction)
    TextView tvRoomIntroduction;
    @Bind(R.id.tv_room_total)
    TextView tvRoomTotal;
    @Bind(R.id.rv_room)
    RecyclerView rvRoom;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    private List<Room> roomList = new ArrayList<>();
    private int hotelId = 0;
    private RoomAdapter roomAdapter;
    private String title;

    @Override
    protected void onGetBundle(Bundle bundle) {
        if (bundle != null) {
            hotelId = bundle.getInt(HOTEL_ID);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("白云山分店");
        ivRight.setImageResource(R.drawable.icon_message);
        initRecyclerView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        NetWorkRequest.getHotelById(this, true, hotelId, new IReponseListener<Hotel>() {
            @Override
            public void onSuccess(Hotel data) {
                initViewPager(data.getHotelImgUrl());
                tvTitle.setText(data.getHotelName());
                title = data.getHotelName();
                roomAdapter.update(data.getRoomList());
                tvRoomAddress.setText(data.getAddress());
                tvRoomIntroduction.setText(data.getIntroduce());
                tvRoomTotal.setText("￥ " + data.getRoomList().get(0).getPrice());
            }

            @Override
            public void onFail(String msg, int statusCode) {

            }
        });
    }

    private void initViewPager(final List<Hotel.HotelImgUrlBean> imgUrlBeanList) {
        vpHotel.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgUrlBeanList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(HotelDetailActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ViewGroup.LayoutParams lp = container.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                container.addView(imageView, lp);
                Glide.with(HotelDetailActivity.this).load(imgUrlBeanList.get(position).getPicture()).into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }

    private void initRecyclerView() {
        roomAdapter = new RoomAdapter(this, roomList);
        roomAdapter.setOnClickListener(new RoomAdapter.onClickListener() {
            @Override
            public void onClick(int position, Room room) {
                Dialog dialog = new RoomDetitalDialog(HotelDetailActivity.this, room, title);
                dialog.show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRoom.setLayoutManager(linearLayoutManager);
        rvRoom.setAdapter(roomAdapter);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                Bundle bundle =new Bundle();
                bundle.putInt(HOTEL_ID,hotelId);
                startActivity(bundle,CommentActivity.class);
                break;
        }
    }

}
