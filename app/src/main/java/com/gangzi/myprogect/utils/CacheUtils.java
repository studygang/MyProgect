package com.gangzi.myprogect.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gangzi.myprogect.WelcomeActivity;

/**
 * Created by Administrator on 2017/6/6.
 */

public class CacheUtils {

    public static final String ISFIRST="isFirst";

    public static void putBoolean(Context context,String isfirst, boolean b) {
        SharedPreferences sp=context.getSharedPreferences(isfirst,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(isfirst,b);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String isfirst) {
        SharedPreferences sp=context.getSharedPreferences(isfirst,Context.MODE_PRIVATE);
        return sp.getBoolean(isfirst,false);
    }
}
