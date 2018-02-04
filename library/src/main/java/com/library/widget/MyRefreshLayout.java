package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.library.widget.refreshlayout.RefreshLayout;

/**
 * 解决与BannerLayout的滑动冲突
 * Created by z on 2016/8/3.
 */
public class MyRefreshLayout extends RefreshLayout {
    private BannerLayout bannerLayout;

    public MyRefreshLayout(Context context) {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float downX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (bannerLayout != null && bannerLayout.isTouching && Math.abs(ev.getX() - downX) > 10) {
                    return false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (bannerLayout != null) {
                    bannerLayout.isTouching = false;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }
    public void setBannerLayout(BannerLayout bannerLayout) {
        this.bannerLayout = bannerLayout;
    }
}
