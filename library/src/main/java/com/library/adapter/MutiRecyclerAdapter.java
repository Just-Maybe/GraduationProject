package com.library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by longbh on 16/6/1.
 */
public abstract class MutiRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> datas;
    protected Context context;

    public MutiRecyclerAdapter(Context context, List<T> datas) {
        super();
        this.datas = datas;
        this.context = context;
    }

    /**
     * 更新数据
     *
     * @param datas
     */
    public void updateDatas(List<T> datas) {
        if (datas != null) {
            this.datas = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据
     *
     * @param datas
     */
    public void addMoreDatas(List<T> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.build(datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        }
        return 0;
    }
}
