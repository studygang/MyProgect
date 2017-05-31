package com.gangzi.myprogect.ui.news.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface NewsTypeList {
    void getNewsdData(String url,String type,String key, StringCallback stringCallback);
}
