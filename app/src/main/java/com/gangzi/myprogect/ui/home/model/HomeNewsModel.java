package com.gangzi.myprogect.ui.home.model;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface HomeNewsModel {
    void getNews(String url, String key, StringCallback stringCallback);
}
