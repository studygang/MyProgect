package com.gangzi.myprogect.ui.home.presenter.imp;

import android.os.AsyncTask;

import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.home.model.HomeNewsModel;
import com.gangzi.myprogect.ui.home.model.imp.HomeNewsModelImp;
import com.gangzi.myprogect.ui.home.presenter.HomeNewsPresenter;
import com.gangzi.myprogect.ui.home.view.HomeNewsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeNewsPresenterImp implements HomeNewsPresenter {

    private HomeNewsModel mHomeNewsModel;
    private HomeNewsView mHomeNewsView;

    public HomeNewsPresenterImp( HomeNewsView mHomeNewsView) {
        this.mHomeNewsView=mHomeNewsView;
        this.mHomeNewsModel=new HomeNewsModelImp();

    }

    @Override
    public void RequestNews(String url,String key) {
        mHomeNewsModel.getNews(url, key, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mHomeNewsView.returnNews(response);
            }
        });
    }
}
