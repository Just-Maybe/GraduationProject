package com.library.activity;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

/**
 * Author : zhouyx
 * Date   : 2016/9/29
 */
public class BaseApplication extends MultiDexApplication {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };
    
    public static Context context;

    /**
     * 自定义Toast,防止用户无止境的点击，然后无止境的弹出toast。
     * 可自定义toast显示的时间
     *
     * @param text
     */
    public static void showToast(String text) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r, 2000);
        mToast.show();
    }

    public void onCreate() {
        super.onCreate();
        context = this;
    }

}
