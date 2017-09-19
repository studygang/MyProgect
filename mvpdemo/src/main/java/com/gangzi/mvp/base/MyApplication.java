package com.gangzi.mvp.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by dan on 2017/9/19.
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }
}
