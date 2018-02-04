package com.library.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.library.R;
import com.library.dialog.DialogLoading;
import com.library.dto.MessageEvent;
import com.library.utils.AppManager;
import com.library.utils.CheckUtil;
import com.library.utils.LogUtil;
import com.library.utils.SdCardUtil;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * https://github.com/HotBitmapGG/bilibili/tree/OhMyBiliBili/app/src/main/java/com/hotbitmapgg/ohmybilibili/base
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * Activity基类
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    private static final int REQUEST_PERMISSION = 0;
    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final int REQUEST_CODE_PHOTO = 2001;
    public static final int REQUEST_CODE_PHOTO_DEAL = 3001;
    public int screenWidth = 0;
    public int screenHeight = 0;
    public Resources res;
    protected BaseActivity context;
    private DialogLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化控件
        setContentView(getViewId());
//        getPerMissions();
        context = this;
        loading = new DialogLoading(context);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        res = getResources();
        AppManager.get().addActivity(this);
        onGetBundle(getIntent().getExtras());
        init(savedInstanceState);
        initListener();
        if (isRegisterEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }


    /**
     * 获取权限
     */
    public void getPerMissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCamaerPermission = checkSelfPermission(Manifest.permission.CAMERA);
            int hasAudioPermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            List<String> permissions = new ArrayList<String>();
            if (hasCamaerPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (hasAudioPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(context);
        if (isRegisterEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }

    public void finishSimple() {
        super.finish();
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(String bundleName, Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtra(bundleName, bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 从底部弹出
     *
     * @param bundle
     * @param target
     */
    public void startActivityBottomToTop(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    /**
     * 带回调的跳转
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * RxJava线程调度
     * 与Activity的生命周期绑定（在Destroy方法中取消订阅）
     * subscribeOn指定观察者代码运行的线程
     * observerOn()指定订阅者运行的线程
     * <p>
     * 提供给它一个Observable它会返回给你另一个Observable
     *
     * @param <T>
     * @return
     */
    public <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> mObservable = (Observable<T>) observable
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(), ActivityEvent.DESTROY))//onDestory方法中取消请求
                        .subscribeOn(Schedulers.io

                                ())
                        .observeOn(AndroidSchedulers.mainThread());
                return mObservable;
            }
        };
    }

    /**
     * 显示加载弹窗
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !loading.isShowing()) {
                    loading.show();
                }
            }
        });
    }

    /**
     * 关闭加载弹窗
     */
    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && loading.isShowing()) {
                    loading.dismiss();
                }
            }
        });
    }

    /**
     * 打开照相
     */
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(SdCardUtil.TEMP));
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void openCamera(int code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(SdCardUtil.TEMP));
        startActivityForResult(intent, code);
    }

    /**
     * 打开照相
     */
    public void openCamera(String path) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse("file:///" + path));
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 打开照片选择
     */
    public void pickUpPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    /**
     * 打开照片选择
     */
    public void pickUpPhoto(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, code);
    }

    /*
     * 对图片进行剪裁，通过Intent来调用系统自带的图片剪裁API
     */
    protected void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        /* aspectX aspectY 是裁剪后图片的宽高比 */
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);
        /* outputX outputY 是裁剪图片宽 */
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("noFaceDetection", true);// 关闭人脸
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(SdCardUtil.TEMP));
        startActivityForResult(intent, REQUEST_CODE_PHOTO_DEAL);
    }

    /*
     * 对图片进行剪裁，通过Intent来调用系统自带的图片剪裁API
     */
    protected void cropPhotoTop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        /* aspectX aspectY 是裁剪后图片的宽高比 */
        intent.putExtra("aspectX", 64);
        intent.putExtra("aspectY", 45);
        /* outputX outputY 是裁剪图片宽 */
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 450);
        intent.putExtra("noFaceDetection", true);// 关闭人脸
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(SdCardUtil.TEMP));
        startActivityForResult(intent, REQUEST_CODE_PHOTO_DEAL);
    }

    /**
     * 对图片进行剪裁，通过Intent来调用系统自带的图片剪裁API
     *
     * @param uri
     * @param filePath
     */
    protected void cropPhoto(Uri uri, String filePath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        /* aspectX aspectY 是裁剪后图片的宽高比 */
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);
        /* outputX outputY 是裁剪图片宽 */
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        intent.putExtra("noFaceDetection", true);// 关闭人脸
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(filePath));
        startActivityForResult(intent, REQUEST_CODE_PHOTO_DEAL);
    }

    /**
     * 对图片进行剪裁，通过Intent来调用系统自带的图片剪裁API
     *
     * @param uri
     * @param filePath
     */
    protected void cropPhoto(Uri uri, int requestCode, String filePath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        /* aspectX aspectY 是裁剪后图片的宽高比 */
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);
        /* outputX outputY 是裁剪图片宽 */
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        intent.putExtra("noFaceDetection", true);// 关闭人脸
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(filePath));
        startActivityForResult(intent, requestCode);
    }

    public void showMessage(final Object message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (CheckUtil.checkEquels(message, "token超时")) {
                    return;
                }
//                Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
                showToast(message + "");
            }
        });
    }


    private Toast mToast;
    private Handler mHandler = new Handler();
    private Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    /**
     * 自定义Toast,防止用户无止境的点击，然后无止境的弹出toast。
     * 可自定义toast显示的时间
     *
     * @param text
     */
    public void showToast(String text) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r, 2000);
        mToast.show();
    }

    protected abstract void onGetBundle(Bundle bundle);

    protected abstract int getViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initListener();


    /**
     * 初始化toolbar
     *
     * @param toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(getTitle());
        }
        toolbar.setMinimumWidth(screenWidth);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setMinimumWidth(screenWidth);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setToolbar(Toolbar toolbar, String title, int backImage) {
        toolbar.setNavigationIcon(backImage);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setMinimumWidth(screenWidth);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setToolbar(Toolbar toolbar, String title, int Color, int backImage) {
        toolbar.setNavigationIcon(backImage);
        toolbar.setBackgroundColor(Color);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setMinimumWidth(screenWidth);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setUnBackToolBar(Toolbar toolbar) {
        toolbar.setMinimumWidth(screenWidth);
        toolbar.setMinimumHeight(30);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setUnBackToolBar(Toolbar toolbar, String title, int Color) {
        toolbar.setBackgroundColor(Color);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setMinimumWidth(screenWidth);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

//    如果子view是自定义的view需要告诉刷新控件是否可以刷新或者加载可以实现此接口
//    mRingRefreshView.setOnCheckCanRefreshListener(new OnCheckCanRefreshListener() {
//        @Override
//        public boolean checkCanDoRefresh() {
//            return mScrollLayout.canRefresh();
//        }
//    });
//    mRingRefreshView.setOnCheckCanLoadMoreListener(new OnCheckCanLoadMoreListener() {
//        @Override
//        public boolean checkCanDoLoadMore() {
//            return mScrollLayout.canLoadMore();
//        }
//    });

    /**
     * EventBus 事件接收处理，子类重写该方法 (主线程运行)
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

    /**
     * EventBus 重写该方法并返回true开启EventBus
     */
    protected boolean isRegisterEventBusHere() {
        return false;
    }

    /**
     * 发送验证码倒计时
     */
    protected void SendCodeTimeDownRecord(final TextView tvGetCode) {
        CountDownTimer countDownTimer = new CountDownTimer(1000*60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
//                LogUtil.d("millisUntilFinished",millisUntilFinished);
                tvGetCode.setEnabled(false);
                tvGetCode.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText("获取验证码");

            }
        };
        countDownTimer.start();
    }

}
