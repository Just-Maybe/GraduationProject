package com.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by longbh on 16/6/1.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData;
    private int resource;
    protected Context mContext;

    public RecyclerAdapter(List<T> data, int resource) {
        super();
        this.mData = data;
        this.resource = resource;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                resource, parent, false);
        BaseViewHolder holder = holder(view, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.get(position) != null) {
            holder.build(mData.get(position), position, mData);
            holder.build(mData.get(position), position);
        }
    }

    public void clear() {
        mData.clear();
    }

    public void add(@NonNull T dto) {
        mData.add(dto);
    }

    public void addAll(@NonNull List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public abstract BaseViewHolder holder(View view, int viewType);


    @Override
    public int getItemCount() {
        return mData.size();
    }

}
