package com.library.http;


import com.library.utils.LogUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

 /**
 * 类描述:网络请求回调接口
 * 作者:xues
 * 时间:2017年04月15日
 */

public abstract class RequestCallBack<T> extends Subscriber<JsonResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.simpleLog("Throwable=" + e.toString());
        String errorMessage = "请求失败";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ResponseBody body = httpException.response().errorBody();
            try {
                JsonResult result = JsonUtil.fromJson(body.string(), JsonResult.class);
                if (result != null) {
                    errorMessage = result.message;
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            e.printStackTrace();
        }
        fail(-1, errorMessage);

    }

    @Override
    public void onNext(JsonResult<T> response) {
        //已经移除订阅，return
        if (isUnsubscribed()) {
            return;
        }

        try {
            if (response == null) {
                fail(-1, "网络请求失败");
            } else {
                if (response.isOk()) {
                    success(response.object);
                } else {
                    fail(response.code, response.message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public abstract void success(T response);

    public abstract void fail(int errCode, String message);
}
