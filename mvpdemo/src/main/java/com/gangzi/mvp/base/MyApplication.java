package com.gangzi.mvp.base;

import android.app.Application;
import android.content.Context;

import com.gangzi.mvp.callback.CustomCallback;
import com.gangzi.mvp.callback.EmptyCallback;
import com.gangzi.mvp.callback.ErrorCallback;
import com.gangzi.mvp.callback.LoadingCallback;
import com.gangzi.mvp.callback.TimeoutCallback;
import com.kingja.loadsir.core.LoadSir;

/**
 * Created by dan on 2017/9/19.
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
    public static Context getContext(){
        return mContext;
    }
}
