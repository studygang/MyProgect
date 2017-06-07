package com.gangzi.myprogect.api;

import com.gangzi.myprogect.entity.News;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface ApiService {
    public static final String MOVIE_API="http://op.juhe.cn/onebox/movie/video";
    public static final String NEWS_API="http://v.juhe.cn/toutiao/index";

    public static final String BASE_NEWS_API="http://v.juhe.cn/";

    //获取所有新闻---retrofit
    @FormUrlEncoded
    @POST("toutiao/index")
    Call<News>getAllNewsByRetrofit(@Field("key") String key);

    //获取所有新闻--RxJava+Retrofit
    @FormUrlEncoded
    @POST("toutiao/index")
    Observable<News>getAllNewsByRxJavaAndRetrofit(@Field("key") String key);

}
