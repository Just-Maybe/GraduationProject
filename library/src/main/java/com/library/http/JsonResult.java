package com.library.http;

public class JsonResult<T> {

    public T object;
    public int code;
    public String message;

    public boolean isOk() {
        if(code == 1){
            return true;
        }
        return false;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }
}
