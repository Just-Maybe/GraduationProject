package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.R;

/**
 * 类描述:提示布局(加载中,无网络,空值提示,漏掉了出错)还有很大的优化空间
 * 作者:xues
 * 时间:2016年10月22日
 */

public class TipLayout extends RelativeLayout {


    private View emptyView;
    private View netErrorView;

    /**
     * 空数据提示文本
     */
    private TextView tvEmpty;
    /**
     * 空数据提示图片
     */
    private ImageView ivEmpty;
    public TipLayout(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public TipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public TipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(final Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        emptyView = inflater.inflate(R.layout.tiplayout_no_data, null);
        netErrorView = inflater.inflate(R.layout.tiplayout_network_error, null);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(params);
        netErrorView.setLayoutParams(params);

        addView(emptyView);
        addView(netErrorView);
        tvEmpty = (TextView) emptyView.findViewById(R.id.tvNoData);
        ivEmpty = (ImageView) emptyView.findViewById(R.id.ivNoData);

        //重新加载点击事件
        netErrorView.findViewById(R.id.ivNetErro).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadClick != null) {
                    onReloadClick.onReload();
                }
            }
        });
    }


    private void resetStatus() {
        this.setVisibility(VISIBLE);
        emptyView.setVisibility(View.GONE);
        netErrorView.setVisibility(View.GONE);
    }
    /**
     * 现实文本内容
     */
    public void showContent() {
        setVisibility(GONE);
    }
    /**
     * 显示没有网络
     */
    public void showNetError() {
        setBackgroundResource(R.color.white);
        resetStatus();
        netErrorView.setVisibility(VISIBLE);
    }
    /**
     * 显示空数据
     */
    public void showEmpty() {
        setBackgroundResource(R.color.white);
        resetStatus();
        emptyView.setVisibility(VISIBLE);
    }
    /**
     * 设置空值图片
     *
     * @param resId 图片id
     */
    public void setEmptyImageResource(int resId) {
        ivEmpty.setImageResource(resId);
    }
    /**
     * 设置空值文本
     *
     * @param emptyText 空值文本
     */
    public void setEmptyText(String emptyText) {
        tvEmpty.setText(emptyText);
    }
    /**
     * 重新加载点击事件
     */
    OnReloadClick onReloadClick;

    public void setOnReloadClick(OnReloadClick onReloadClick) {
        this.onReloadClick = onReloadClick;
    }

    public interface OnReloadClick {
        void onReload();
    }
    /**
     * 数据为空,底部按钮点击事件
     */
    OnEmptyOpClick onEmptyOpClick;

    public void setOnEmptyOpClick(OnEmptyOpClick onEmptyOpClick) {
        this.onEmptyOpClick = onEmptyOpClick;
    }

    public interface OnEmptyOpClick {
        void onEmptyOpClick();
    }
}
