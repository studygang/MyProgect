package com.gangzi.myprogect.ui.type.presenter.imp;

import com.gangzi.myprogect.ui.type.model.TypeNewsData;
import com.gangzi.myprogect.ui.type.model.imp.NewsTypeDataImp;
import com.gangzi.myprogect.ui.type.presenter.NewsTypePresenter;
import com.gangzi.myprogect.ui.type.view.NewsTypeView;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NewsTypePresenterImp implements NewsTypePresenter{
    private TypeNewsData mTypeNewsData;
    private NewsTypeView mNewsTypeView;

    public NewsTypePresenterImp( NewsTypeView newsTypeView) {
        this.mTypeNewsData = new NewsTypeDataImp();
        this.mNewsTypeView = newsTypeView;
    }

    @Override
    public void requestNews(String url, String type, String key) {
        mTypeNewsData.getTypeNewsData(url, type, key, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mNewsTypeView.returnData(response);
            }
        });
    }

}
