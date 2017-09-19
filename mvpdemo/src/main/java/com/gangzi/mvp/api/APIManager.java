package com.gangzi.mvp.api;


import com.gangzi.mvp.base.MyApplication;
import com.gangzi.mvp.bean.WeChatData;
import com.gangzi.mvp.utils.StateUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gangzi on 2017/6/23.
 */

public class APIManager {
    private static final String BASE_URL="http://v.juhe.cn";
   // private static final String key="f6db366d5a4acbc5e75864c8435eff2f";

    private static final int READ_TIMEOUT=10;
    private static final int WRITE_TIMEOUT=10;
    private static final int CONNECT_TIMEOUT=10;

    private static OkHttpClient client;

    public APIManager() {
        //cache url
        File httpCacheDirectory = new File(MyApplication.mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!StateUtils.isNetworkAvailable(MyApplication.mContext)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

            }
            Response originalResponse = chain.proceed(request);
            if (StateUtils.isNetworkAvailable(MyApplication.mContext)) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

              client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache).build();
    }

    private static final Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
            .client(new APIManager().client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 使用RxJava作为回调适配器
            .build();

    private static final APIManagerService apiManager=retrofit.create(APIManagerService.class);

    public static Observable<WeChatData> getWeXinChat(int pno, int ps, String key, String dtype) {
        return apiManager.getWeXinChat(pno,ps,key,dtype);
    }

}
