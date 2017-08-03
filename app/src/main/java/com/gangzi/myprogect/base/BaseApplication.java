package com.gangzi.myprogect.base;

import android.content.Context;

import com.mob.MobApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/6/6.
 */

public class BaseApplication extends MobApplication {
    private static BaseApplication baseApplication;
    private static OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication=this;
        initOkHttpClient();
    }
    public static Context getAppContext(){
        return baseApplication;
    }

    private void initOkHttpClient() {
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    public static OkHttpClient getmOkHttpClient(){
       return mOkHttpClient;
    }
}
