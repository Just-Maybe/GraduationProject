package com.example.miracle.graduationproject.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.api.NetWorkRequest;
import com.example.miracle.graduationproject.dto.Order;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.example.miracle.graduationproject.utils.HawkKey;
import com.library.activity.BaseActivity;
import com.library.adapter.CommenDelegateAdapter;
import com.library.adapter.RecyclerViewHolder;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/1/21 0021.
 */

public class OrderActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.rg_order)
    RadioGroup rgOrder;
    @Bind(R.id.rv_order)
    RecyclerView rvOrder;
    private CommenDelegateAdapter<Order> currentOrder;
    private CommenDelegateAdapter<Order> historyOrder;
    private DelegateAdapter delegateAdapter;

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("我的订单");
        initRecyclerView();
        getOrderList(Hawk.get(HawkKey.USER_ID, 0), 1, null);
        //lllll
    }

    /**
     * 列表
     */
    private void initRecyclerView() {

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        rvOrder.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        currentOrder = new CommenDelegateAdapter<Order>(this, R.layout.item_order, null, linearLayoutHelper, 0) {
            @Override
            public void convert(RecyclerViewHolder holder, Order order, int position) {
                holder.setText(R.id.tv_order_address, order.getHotelName());
                holder.setText(R.id.tv_order_no, order.getOrderNo() + "");
                holder.setText(R.id.tv_order_total, "￥ " + order.getPrice());
                holder.setText(R.id.tv_enter_time, order.getLiveTime());
                holder.setText(R.id.tv_quit_time, order.getLeaveTime());
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        LinearLayoutHelper linearLayoutHelper2 = new LinearLayoutHelper();
        historyOrder = new CommenDelegateAdapter<Order>(this, R.layout.item_order, null, linearLayoutHelper2, 0) {
            @Override
            public void convert(RecyclerViewHolder holder, Order order, int position) {
                holder.setText(R.id.tv_order_address, order.getHotelName());
                holder.setText(R.id.tv_order_no, order.getOrderNo() + "");
                holder.setText(R.id.tv_order_total, "￥ " + order.getPrice());
                holder.setText(R.id.tv_enter_time, order.getLiveTime());
                holder.setText(R.id.tv_quit_time, order.getLeaveTime());
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };
        delegateAdapter.addAdapter(currentOrder);
        delegateAdapter.addAdapter(historyOrder);
        rvOrder.setAdapter(delegateAdapter);
    }

    @Override
    protected void initListener() {
        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_current_order:
                        getOrderList(Hawk.get(HawkKey.USER_ID, 0), 1, 1);
                        break;
                    case R.id.rb_history_order:
                        getOrderList(Hawk.get(HawkKey.USER_ID, 0), 5, 1);
                        break;
                }
            }
        });
    }

    public void getOrderList(int userId, final int status, Integer page) {
        NetWorkRequest.getOrderByUserId(OrderActivity.this, true, userId, status, page, new IReponseListener<List<Order>>() {
            @Override
            public void onSuccess(List<Order> data) {
                if (data != null && data.size() > 0) {
                    if (status == 5) {
                        historyOrder.upDateData(data);
                        currentOrder.setItemNum(0);
                        currentOrder.upDateData(null);
                        return;
                    }
                    currentOrder.upDateData(data);
                    historyOrder.setItemNum(0);
                    historyOrder.upDateData(null);
                }
            }

            @Override
            public void onFail(String msg, int statusCode) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
