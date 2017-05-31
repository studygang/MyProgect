package com.gangzi.myprogect.ui.news.presenter.imp;

import com.gangzi.myprogect.ui.news.model.NewsTypeList;
import com.gangzi.myprogect.ui.news.model.imp.NewTypeListImp;
import com.gangzi.myprogect.ui.news.presenter.NewsTypeListPressenter;
import com.gangzi.myprogect.ui.news.view.NewsTypeListView;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NewsTypeListPressenterImp implements NewsTypeListPressenter,NewsTypeListView {

    private NewsTypeListView mNewsTypeListView;
    private NewsTypeList mNewsTypeList;

    public NewsTypeListPressenterImp(NewsTypeListView newsTypeListView) {
        this.mNewsTypeListView=newsTypeListView;
        this.mNewsTypeList=new NewTypeListImp();
    }

    @Override
    public void requestNewsTypeListData(String type, String url,String key) {
        mNewsTypeList.getNewsdData(url,type,key,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mNewsTypeListView.returnNews(response);
            }
        });
    }

    @Override
    public void returnNews(String result) {

    }
}
