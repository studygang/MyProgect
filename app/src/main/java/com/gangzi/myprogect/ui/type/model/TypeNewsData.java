package com.gangzi.myprogect.ui.type.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface TypeNewsData {
    void getTypeNewsData(String url,String type,String key,StringCallback stringCallback);
}
