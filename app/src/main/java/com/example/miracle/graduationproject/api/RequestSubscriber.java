package com.example.miracle.graduationproject.api;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.miracle.graduationproject.dto.RequestEntity;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.example.miracle.graduationproject.utils.DialogUtil;
import com.example.miracle.graduationproject.utils.NetworkUtil;

import rx.Subscriber;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class RequestSubscriber<T> extends Subscriber<RequestEntity<T>> {
    Context mContext;
    IReponseListener mListener;
    private Dialog mLoadingDialog;
    private boolean isShowDialog;

    public RequestSubscriber(Context mContext, boolean isShowDialog, IReponseListener mListener) {
        this.mListener = mListener;
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
        if (isShowDialog) {
            mLoadingDialog = DialogUtil.createLoadingDialog(mContext, "正在加载");
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.show();
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (isShowDialog) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
        }
        if (!NetworkUtil.isNetworkConnected(mContext.getApplicationContext())) {
            mListener.onFail("当前网络不可用,请检查您的网络设置", 400);
            return;
        }
        mListener.onFail("服务器异常，请稍后重试！", 400);
    }

    @Override
    public void onNext(RequestEntity<T> requestEntity) {
        if (isShowDialog) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
        }

        if (!NetworkUtil.isNetworkConnected(mContext.getApplicationContext())) {
            Toast.makeText(mContext.getApplicationContext(), "当前网络不可用,请检查您的网络设置", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestEntity.getStatusCode() == 200) {
            mListener.onSuccess(requestEntity.getData());
            return;
        }
        mListener.onFail(requestEntity.getMessage(), requestEntity.getStatusCode());
    }
}
