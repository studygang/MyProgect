package com.gangzi.myprogect.ui.home.model.imp;

import com.gangzi.myprogect.api.ApiService;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.home.model.HomeNewsModel;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeNewsModelImp implements HomeNewsModel {

    @Override
    public void getNews(String url,String key,StringCallback stringCallback) {
        OkHttpUtils.get().url(url).addParams("key",key).build().execute(stringCallback );
    }

    @Override
    public void getNewsByRetrofit(String url, String key, Callback<News> callback) {
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<News>call=apiService.getAllNewsByRetrofit(key);
        call.enqueue(callback);
    }

    @Override
    public void getNewsByRetrofitAndRxJava(String url, String key, Observer<News> observer) {
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        ApiService service=retrofit.create(ApiService.class);
        Observable<News> observable=service.getAllNewsByRxJavaAndRetrofit(key);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
