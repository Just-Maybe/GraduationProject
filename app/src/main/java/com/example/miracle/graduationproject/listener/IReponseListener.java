package com.example.miracle.graduationproject.listener;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public interface IReponseListener<T> {
    void onSuccess(T data);

    void onFail(String msg, int statusCode);
}
