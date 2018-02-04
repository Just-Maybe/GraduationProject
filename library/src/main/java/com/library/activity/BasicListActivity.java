package com.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.library.R;
import com.library.widget.TipLayout;
import com.library.widget.refreshlayout.RefreshLayout;
import com.library.widget.refreshlayout.RefreshLayoutDirection;

/**
 * Created by longbh on 16/5/31.
 */
public abstract class BasicListActivity extends BaseActivity {

    protected RecyclerView listView;
    protected RefreshLayout refreshLayout;
    protected TipLayout tipLayout;
    protected int page = 1;
    protected Button rightBtn;
    protected boolean isCread = true;
    @Override
    protected int getViewId() {
        return R.layout.activity_list_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        refreshLayout = (RefreshLayout) findViewById(R.id.ref_layout);
        tipLayout = (TipLayout) findViewById(R.id.tipLayout);
        listView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.setAdapter(getAdapter());
        loadData(page);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
                page = 1;
                loadData(page);
            }

            @Override
            public void onPullUpToRefresh() {
                page++;
                loadData(page);
            }
        });
    }

    protected boolean isAddItemDecoration() {
        if(isCread){
            isCread = false;
            return true;
        }
        return false;
    }
    public abstract void loadData(int page);

    public abstract RecyclerView.Adapter getAdapter();

    public void onLoad(int size) {
        if (refreshLayout == null) {
            return;
        }
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (size < 10) {
            refreshLayout.setDirection(RefreshLayoutDirection.TOP);
        } else {
            refreshLayout.setDirection(RefreshLayoutDirection.BOTH);
        }
    }
}
