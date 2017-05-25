package com.gangzi.myprogect.ui.home.model.imp;

import com.gangzi.myprogect.http.okhttp.OkHttpRequest;
import com.gangzi.myprogect.ui.home.model.HomeNewsModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeNewsModelImp implements HomeNewsModel {

    @Override
    public void getNews(String url,String key,StringCallback stringCallback) {
        OkHttpUtils.get().url(url).addParams("key",key).build().execute(stringCallback );
    }
}
