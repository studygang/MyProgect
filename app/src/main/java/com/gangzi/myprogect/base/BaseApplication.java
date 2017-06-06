package com.gangzi.myprogect.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/6.
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication=this;
    }
    public static Context getAppContext(){
        return baseApplication;
    }

}
