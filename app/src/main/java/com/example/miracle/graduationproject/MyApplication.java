package com.example.miracle.graduationproject;

import android.app.Application;

import com.library.utils.glide.GlideUtil;
import com.orhanobut.hawk.Hawk;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        GlideUtil.init(this);
    }
}
