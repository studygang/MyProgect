package com.gangzi.myprogect.api;

import com.gangzi.myprogect.entity.News;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface ApiService {
    public static final String MOVIE_API="http://op.juhe.cn/onebox/movie/video";
    public static final String NEWS_API="http://v.juhe.cn/toutiao/index";
    //获取所有新闻
    @GET("key")
    Observable<Map<String,List<News>>>getAllNews(@Path("key") String key);

}
