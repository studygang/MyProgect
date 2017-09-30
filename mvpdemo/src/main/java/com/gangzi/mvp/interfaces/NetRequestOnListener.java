package com.gangzi.mvp.interfaces;


/**
 * Created by gangzi on 2017/6/23.
 */

public interface NetRequestOnListener<T>{
    void onSuccess(T s);
    void onFailure(Throwable e);
    void refresh(T s);
    void loadMore(T s);
    void onFail(String msg);
}
