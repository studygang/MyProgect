package com.gangzi.mvp.ui.view.imp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.gangzi.mvp.R;
import com.gangzi.mvp.base.BaseActivity;
import com.gangzi.mvp.bean.WeChatData;
import com.gangzi.mvp.ui.presenter.imp.WeChatPresenterImp;
import com.gangzi.mvp.ui.view.WeChatView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dan on 2017/9/18.
 */

public class WeChatActivity extends BaseActivity <WeChatView,WeChatPresenterImp> implements WeChatView{

    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    private int pno=1;
    private int currentPager;
    private int totalPager=2;
    @BindView(R.id.tv_text)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        initData();
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        presenter.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void loadWeChat(WeChatData weChatData) {
        Gson gson=new Gson();
        String result=gson.toJson(weChatData);
        mTextView.setText(result);
        System.out.println("--------------result---"+result);
    }

    @Override
    public void refreshData(WeChatData weChatData) {

    }

    @Override
    public void loadMoreData(WeChatData weChatData) {

    }

    @Override
    public WeChatPresenterImp initPresenter() {
        return new WeChatPresenterImp(this);
    }
}
