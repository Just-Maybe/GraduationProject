package com.example.miracle.graduationproject.api;

import android.content.Context;

import com.google.gson.Gson;
import com.library.http.RequestHeaderInterceptor;
import com.library.http.RequestLogInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longbh on 16/5/24.
 */
public class Http {

    public static String user_session = "";
//    public static String baseUrl = "http://localhost:8080/";
//    public static String baseUrl = "http://172.168.6.171:8080/";
//    public static String baseUrl = "http://192.168.0.196:8080/";
    public static String baseUrl = "http://192.168.191.1:8080/";

    public static Http http;
    public static Gson gson;
    private Retrofit mRetrofit;

    private Http(Context context) {

        File httpCacheDirectory = new File(context.getApplicationContext().getCacheDir(), context
                .getApplicationContext().getPackageName());
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static boolean isLogin() {
        return !"".equals(user_session);
    }

    public static Http initHttp(Context context) {
        return new Http(context);
    }

    public <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public RetrofitApi getApi() {
        return mRetrofit.create(RetrofitApi.class);
    }

}
