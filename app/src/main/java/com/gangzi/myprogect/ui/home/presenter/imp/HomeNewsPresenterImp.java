package com.gangzi.myprogect.ui.home.presenter.imp;

import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.home.model.HomeNewsModel;
import com.gangzi.myprogect.ui.home.model.imp.HomeNewsModelImp;
import com.gangzi.myprogect.ui.home.presenter.HomeNewsPresenter;
import com.gangzi.myprogect.ui.home.view.HomeNewsView;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
       /* mHomeNewsModel.getNews(url, key, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mHomeNewsView.returnNews(response);
            }
        });*/
     /*  mHomeNewsModel.getNewsByRetrofit(url, key, new Callback<News>() {
           @Override
           public void onResponse(Call<News> call, Response<News> response) {
               News news=response.body();
               String result=new Gson().toJson(news);
               System.out.println("------result-retrofit------"+result);
               mHomeNewsView.returnNews(result);
           }

           @Override
           public void onFailure(Call<News> call, Throwable t) {

           }
       });*/
     mHomeNewsModel.getNewsByRetrofitAndRxJava(url, key, new Observer<News>() {
         @Override
         public void onSubscribe(Disposable d) {

         }

         @Override
         public void onNext(News value) {
             String result=new Gson().toJson(value);
             System.out.println("------result-retrofit------"+result);
             mHomeNewsView.returnNews(result);
         }

         @Override
         public void onError(Throwable e) {

         }

         @Override
         public void onComplete() {

         }
     });
    }
}
