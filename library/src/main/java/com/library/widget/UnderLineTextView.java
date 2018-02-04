package com.library.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * PackageName : cn.ewhale.quantu.widget
 * Author : SimGa Liu
 * Date : 2017/11/13
 * Time : 17:06
 */
public class UnderLineTextView extends android.support.v7.widget.AppCompatTextView {
    public UnderLineTextView(Context context) {
        this(context, null);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        getPaint().setAntiAlias(true);//抗锯齿
    }
}
