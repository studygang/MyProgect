package com.gangzi.myprogect.ui.home.model;

import com.gangzi.myprogect.entity.News;
import com.zhy.http.okhttp.callback.StringCallback;


import org.reactivestreams.Subscriber;

import java.util.Map;

import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface HomeNewsModel {
    void getNews(String url, String key, StringCallback stringCallback);
    void getNewsByRetrofit(String url, String key, Callback<News> callback);
    void getNewsByRetrofitAndRxJava(String url, String key,Observer<News> observer);
}
