package com.example.miracle.graduationproject.api;

import android.content.Context;

import com.example.miracle.graduationproject.dto.Comment;
import com.example.miracle.graduationproject.dto.Hotel;
import com.example.miracle.graduationproject.dto.Order;
import com.example.miracle.graduationproject.listener.IReponseListener;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class NetWorkRequest {

    /**
     * 注册
     *
     * @param context
     * @param username
     * @param password
     * @param listener
     */
    public static void register(Context context, String username, String password, IReponseListener listener) {
        Http.initHttp(context).getApi().register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber(context, true, listener));
    }

    /**
     * 登录
     *
     * @param context
     * @param isShowDialog
     * @param username
     * @param password
     * @param listener
     */
    public static void login(Context context, boolean isShowDialog, String username, String password, IReponseListener<Integer> listener) {
        Http.initHttp(context).getApi().login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber(context, isShowDialog, listener));
    }


    public static void getHotelById(Context context, boolean isShowDialog, Integer hotelId, IReponseListener<Hotel> listener) {
        Http.initHttp(context).getApi().getHotelById(hotelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber<Hotel>(context, isShowDialog, listener));
    }

    public static void searchHotel(Context context, boolean isShowDialog, String searchContent, IReponseListener<List<Hotel>> listener) {
        Http.initHttp(context).getApi().searchHotel(searchContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber<List<Hotel>>(context, isShowDialog, listener));
    }

    public static void getOrderByUserId(Context context, boolean isShowDialog, Integer userId, Integer status, Integer page, IReponseListener<List<Order>> listener) {
        Http.initHttp(context).getApi().getOrderByUserId(userId, status, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber<List<Order>>(context, isShowDialog, listener));
    }

    public static void getCommentByHotelId(Context context, Integer hotelId, Integer page, IReponseListener<List<Comment>> listener) {
        Http.initHttp(context).getApi().getCommentByHotelId(hotelId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber<List<Comment>>(context, true, listener));
    }

    public static void postComment(Context context, String content, Integer userId, Integer hotelId, IReponseListener<Void> listener) {
        Http.initHttp(context).getApi().postComment(content, userId, hotelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RequestSubscriber<Void>(context, true, listener));
    }
}
