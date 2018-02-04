package com.library.http;

import com.library.utils.CheckUtil;
import com.library.utils.LogUtil;
import com.library.utils.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestHeaderInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        okhttp3.Request.Builder builder = request.newBuilder();
        builder.addHeader("sessionId", Http.user_session);
        LogUtil.e("JIA", "-- : " + Http.user_session);
//        if (!CheckUtil.isNull(Http.user_session)) {
//
//            builder.addHeader("sign", StringUtil.MD5(Http.user_session + "qrwd+gzcxzc-s454545+8mm7dfsdafhhoopqq-x"));
//        } else {
//            builder.addHeader("sign", StringUtil.MD5("qrwd+gzcxzc-s454545+8mm7dfsdafhhoopqq-x"));
//        }
        return chain.proceed(builder.build());
    }
}