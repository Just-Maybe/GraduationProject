package com.example.miracle.graduationproject.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.api.NetWorkRequest;
import com.example.miracle.graduationproject.dto.Hotel;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.library.activity.BaseActivity;
import com.library.adapter.CommenDelegateAdapter;
import com.library.adapter.RecyclerViewHolder;
import com.library.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/1/31 0031.
 */

public class SearchResultActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.rv_result)
    RecyclerView rvResult;
    @Bind(R.id.iv_right)
    ImageView ivSearch;
    private CommenDelegateAdapter<Hotel> resultAdapter;
    public static final String ENTER_TIME = "enter_time";
    public static final String QUIT_TIME = "quit_time";
    public static final String SEARCH_CONTENT = "search_content";
    private String enterTime;
    private String qiutTime;
    private String searchContent = "";

    @Override
    protected void onGetBundle(Bundle bundle) {
        if (bundle != null) {
            enterTime = bundle.getString(ENTER_TIME);
            qiutTime = bundle.getString(QUIT_TIME);
            searchContent = bundle.getString(SEARCH_CONTENT);
            Log.d("TAG", "onGetBundle: " + searchContent);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
        initData(searchContent);
    }

    /**
     * 加载数据
     */
    public void initData(String content) {
        if (StringUtil.isEmpty(content)) {
            showToast("还没输入搜索内容!");
            return;
        }
        NetWorkRequest.searchHotel(this, true, content, new IReponseListener<List<Hotel>>() {
            @Override
            public void onSuccess(List<Hotel> data) {
                resultAdapter.upDateData(data);
            }

            @Override
            public void onFail(String msg, int statusCode) {

            }
        });
    }

    /**
     * 初始化搜索结果列表
     */
    private void initRecyclerView() {

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rvResult.setLayoutManager(virtualLayoutManager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        resultAdapter = new CommenDelegateAdapter<Hotel>(this, R.layout.item_room_info, null, linearLayoutHelper, 0) {
            @Override
            public void convert(RecyclerViewHolder holder, Hotel hotel, int position) {
                holder.setText(R.id.tv_room_address, hotel.getAddress());
                holder.setText(R.id.tv_room_introduction, hotel.getIntroduce());
                if (hotel.getRoomList() != null && hotel.getRoomList().size() > 0) {
                    holder.setText(R.id.tv_room_total, "￥ " + hotel.getRoomList().get(0).getPrice());
                }
                if (hotel.getHotelImgUrl() != null && hotel.getHotelImgUrl().size() > 0) {
                    Glide.with(SearchResultActivity.this).load(hotel.getHotelImgUrl().get(0).getPicture()).into((ImageView) holder.getView(R.id.iv_room_bg));
                }
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        delegateAdapter.addAdapter(resultAdapter);


        rvResult.setAdapter(delegateAdapter);
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
                initData(etSearch.getText().toString());
                break;
        }
    }
}
