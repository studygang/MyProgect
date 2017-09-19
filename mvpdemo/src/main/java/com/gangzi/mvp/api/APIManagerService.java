package com.gangzi.mvp.api;


import com.gangzi.mvp.bean.WeChatData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gangzi on 2017/6/23.
 */

public interface APIManagerService {


    @GET("/weixin/query")
    Observable<WeChatData>getWeXinChat(@Query("pno") int pno, @Query("ps") int ps,
                                       @Query("key") String key, @Query("dtype") String dtype);
}
