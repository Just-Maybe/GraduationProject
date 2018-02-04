package com.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.R;
import com.library.dto.MessageEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * https://github.com/HotBitmapGG/bilibili/tree/OhMyBiliBili/app/src/main/java/com/hotbitmapgg/ohmybilibili/base
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * Fragment基类
 */
public abstract class BaseFragment extends RxFragment {

    protected BaseActivity context;
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        if (isBindEventBusHere() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = setContentView(inflater, getViewId());
//        setFillStatusBar(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
        initListener();
    }


    protected abstract int getViewId();

    protected abstract void init(Bundle savedInstanceState);
    protected abstract void initListener();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    public View setContentView(LayoutInflater inflater, int resId) {
        if (rootView == null) {
            rootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    protected void finish() {
        context.finish();
    }

    protected void finishSimple() {
        context.finish();
    }

    public void finishResult(Intent intent) {
        context.finishResult(intent);
    }

    public void finishResult() {
        context.finishResult();
    }

    public void startActivity(Bundle bundle, Class<?> target) {
        context.startActivity(bundle, target);
        context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

    }

    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(context, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

    }
    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setMinimumWidth(context.screenWidth);
        context.setSupportActionBar(toolbar);
        context. getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context. getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * EventBus 主线程运行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event != null) {
            onEventComing(event);
        }
    }

    /**
     * EventBus 子类重写该方法
     */
    protected void onEventComing(MessageEvent event) {

    }

    /**
     * EventBus 需要注册EventBus的Activity重写该方法,返回true
     */
    protected boolean isBindEventBusHere() {
        return false;
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
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY))//onDestory方法中取消请求
                        .subscribeOn(Schedulers.io

                                ())
                        .observeOn(AndroidSchedulers.mainThread());
                return mObservable;
            }
        };
    }
}
