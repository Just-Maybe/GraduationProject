package com.library.http;


import com.library.utils.LogUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 类描述:多接口请求回调
 * 作者:xues
 * 时间:2017年04月15日
 */

public abstract class ZipRequestCallBack extends Subscriber<List<JsonResult>> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.simpleLog("Throwable=" + e.getMessage());
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
    public void onNext(List<JsonResult> response) {
        if (isUnsubscribed()) {
            return;
        }
        try {
            if (response == null) {
                fail(-1, "网络请求失败");
            } else {
                for (JsonResult jsonResult : response) {
                    if (!jsonResult.isOk()) {
                        fail(-1, jsonResult.message);
                        return;
                    }

                }

                success(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public abstract void success(List<JsonResult> response);

    public abstract void fail(int errCode, String errStr);
}
