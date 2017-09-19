package com.gangzi.mvp.base;

/**
 * Created by dan on 2017/9/18.
 */

public abstract class BasePresenter<T> {

    public T mView;

    public void attach(T mView){
        this.mView=mView;
    }
    public void dettach(){
        mView=null;
    }
}
