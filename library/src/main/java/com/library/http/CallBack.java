package com.library.http;

public abstract class CallBack<T> {
    public void filter(JsonResult<T> response) {
        try {
            if (response == null) {
                fail("网络请求失败",-1);
            } else {
                if (response.isOk()) {
                    success(response.object);
                } else {
                    fail(response.message,response.code);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract void success(T response);

    public abstract void fail(String errorMessage,int status);

}
