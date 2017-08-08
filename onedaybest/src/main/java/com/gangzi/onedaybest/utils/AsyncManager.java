package com.gangzi.onedaybest.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dan on 2017/8/8.
 */

public class AsyncManager{
    private static ExecutorService threadPool = null;
    private static synchronized ExecutorService getThreadPool(){
        if(null==threadPool){
            threadPool= Executors.newCachedThreadPool();
        }
        return threadPool;
    }

    public static void execute(Runnable runnable){
        getThreadPool().execute(runnable);
    }
}
