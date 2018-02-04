package com.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.library.activity.BaseActivity;

import java.util.List;

public abstract class MBaseAdapter<T> extends BaseAdapter {

    protected BaseActivity context;
    private LayoutInflater inflater;
    protected List<T> data;
    private int resouceId;

    public MBaseAdapter(Context context, List<T> data, int resourceId) {
        this.context = (BaseActivity) context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.resouceId = resourceId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resouceId, parent, false);
            newView(convertView, position);
        }
        holderView(convertView, data.get(position), position);
        return convertView;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    /**
     * 更新数据
     *
     * @param datas
     */
    public void updateData(List<T> datas) {
        if (datas != null) {
            this.data = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 尾部加载数据
     *
     * @param datas
     */
    public void addMoreData(List<T> datas) {
        if (datas != null) {
            this.data.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 用于覆盖，在各个其他adapter里边返回id,默认返回position
     *
     * @param position
     * @param object
     * @return
     */
    protected long convertId(int position, Object object) {
        return position;
    }

    /**
     * 第一次创建的时�?调用
     *
     * @param convertView
     */
    protected abstract void newView(View convertView, int position);

    /**
     * 用于数据赋�?等等
     *
     * @param convertView
     * @param itemObject
     */
    protected abstract void holderView(View convertView, T itemObject, int position);
}
