package com.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.library.utils.glide.GlideUtil;

/**
 * Created by Jiajun on 2017/10/27.
 * E-mail  : 1021661582@qq.com
 * Desc    : recycler通用型viewholder
 * Version : 1.0.0
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;


    public RecyclerViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static RecyclerViewHolder get(Context context, ViewGroup parent, int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView, parent);
        return holder;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public RecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setCheck(int viewId, boolean isCheck) {
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(isCheck);
        return this;
    }

    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    //加载网络头像图片
    public RecyclerViewHolder setImageLoadAvatar(int viewId, String url) {
        ImageView view = getView(viewId);
        GlideUtil.loadHead(url, view);
        return this;
    }

    //加载正方形的网络图片
    public RecyclerViewHolder setSquareImageLoad(int viewId, String url) {
        ImageView view = getView(viewId);
        GlideUtil.loadPicture(url, view);
        return this;
    }

    //加载本地图片glide
    public RecyclerViewHolder setImageByGlide(int viewId, int resId) {
        ImageView view = getView(viewId);
        Glide.with(mContext).load(resId).into(view);
        return this;
    }


    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setCheckedChangeListener(int viewId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 长按事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public RecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setViewPager(int viewId, PagerAdapter adapter) {
        ViewPager view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public RecyclerViewHolder setOnPageChangeListener(int viewId, ViewPager.OnPageChangeListener listener) {
        ViewPager view = getView(viewId);
        view.addOnPageChangeListener(listener);
        return this;
    }

    public RecyclerViewHolder setBtnText(int viewId, String text) {
        Button tv = getView(viewId);
        tv.setText(text);
        return this;
    }


    /**
     * 控件显示
     *
     * @param viewId
     * @return
     */
    public RecyclerViewHolder setViewVisible(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 控件隐藏
     *
     * @param viewId
     * @return
     */
    public RecyclerViewHolder setViewGone(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    /**
     * 对Linearlayout移除所有控件，并加入新的view
     *
     * @param viewId
     * @param viewList
     * @return
     */
    public RecyclerViewHolder addViewToLinearLayout(int viewId, View[] viewList) {
        LinearLayout view = getView(viewId);
        view.removeAllViews();
        for (View view1 : viewList) {
            view.addView(view1);
        }
        return this;
    }


    public View getmConvertView() {
        return mConvertView;
    }
}
