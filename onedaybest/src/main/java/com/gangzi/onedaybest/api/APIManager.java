package com.gangzi.onedaybest.api;

import com.gangzi.onedaybest.bean.WeChatData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gangzi on 2017/6/23.
 */

public class APIManager {
    private static final String BASE_URL="http://v.juhe.cn";
   // private static final String key="f6db366d5a4acbc5e75864c8435eff2f";
    private static final Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 使用RxJava作为回调适配器
            .build();

    private static final APIManagerService apiManager=retrofit.create(APIManagerService.class);

    public static Observable<WeChatData> getWeXinChat(int pno, int ps, String key, String dtype) {
        return apiManager.getWeXinChat(pno,ps,key,dtype);
    }

}
