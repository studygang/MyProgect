package com.gangzi.myprogect.ui.type.model.imp;

import com.gangzi.myprogect.ui.type.model.TypeNewsData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NewsTypeDataImp implements TypeNewsData {
    @Override
    public void getTypeNewsData(String url, String type, String key,StringCallback stringCallback) {
        OkHttpUtils.get().url(url).addParams("type",type).addParams("key",key).build().execute(stringCallback);
    }
}
