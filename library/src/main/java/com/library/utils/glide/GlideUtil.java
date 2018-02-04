package com.library.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.library.R;
import com.library.utils.LogUtil;


/**
 * Summary ：加载网络图片使用的工具类，基于Glide
 * Created by zhangdm on 2016/2/20.
 */
public class GlideUtil {
    public static final String TAG = "GlideUtil";

    /**
     * Glide的请求管理器类
     */
    private static RequestManager mRequestManager;
    private static Context mContext;

    /**
     * 初始化Glide工具
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
    }

    /**
     * Glide工具类是否已经初始化
     *
     * @return 已初始化则返回true
     */
    public static boolean isInit() {
        if (mContext == null || mRequestManager == null) {
            LogUtil.i(TAG, TAG + "not init");
            return false;
        }
        return true;
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadPicture(String url, ImageView imageView) {
        loadPicture(url, imageView, R.drawable.default_image);
    }

    /**
     * 加载头像
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadHead(String url, ImageView imageView) {
        loadPicture(url, imageView, R.drawable.default_image);
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadPicture(String url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                //.crossFade(300);
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }


    public static void loadCirclePictrue(String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    //加载高斯模糊图片
    public static void loadBlurPicture(String url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .dontAnimate().bitmapTransform(new com.library.utils.glide.BlurTransformation(mContext, 18, 1));// “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。

        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }

    public static void loadPicture2(String url, GlideDrawableImageViewTarget listener, int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(listener);
    }

    //加载圆角的图片
    public static void loadRoundPicture(String url, int placeholder, ImageView imageView) {
        mRequestManager
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 15))
                .placeholder(placeholder)
                .error(placeholder)
                .into(imageView);
    }

    //加载圆角的图片
    public static void loadRoundPicture(int url, int placeholder, ImageView imageView) {
        mRequestManager
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 5))
                .placeholder(placeholder)
                .into(imageView);
    }
    /**
     * 加载正方形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadOnePictrue(String url, ImageView imageView) {

        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        DrawableRequestBuilder builder = Glide.with(imageView.getContext())
                .load(url)
                .crossFade(300)
                .dontAnimate();
        builder.into(imageView);
    }

    public static void loadPicture(String url, GlideDrawableImageViewTarget listener, int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(listener);
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        Glide.get(mContext).clearMemory();
        Glide.get(mContext).clearDiskCache();
    }
}
