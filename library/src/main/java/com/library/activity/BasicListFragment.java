package com.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.R;
import com.library.widget.TipLayout;
import com.library.widget.refreshlayout.RefreshLayout;
import com.library.widget.refreshlayout.RefreshLayoutDirection;

import butterknife.ButterKnife;

/**
 * Created by longbh on 16/5/31.
 */
public abstract class BasicListFragment extends BaseFragment {
    protected RecyclerView listView;
    protected RefreshLayout refreshLayout;
    protected TipLayout tipLayout;
    protected int page = 1;
    protected boolean isCread = true;

    @Override
    protected int getViewId() {
        return R.layout.fragment_list_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listView = (RecyclerView) rootView.findViewById(R.id.list);
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.ref_layout);
        tipLayout = (TipLayout) rootView.findViewById(R.id.tipLayout);
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

    public abstract void loadData(int page);

    public abstract RecyclerView.Adapter getAdapter();

    protected boolean isAddItemDecoration() {
        if (isCread) {
            isCread = false;
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
