package com.gangzi.onedaybest.pressenter.imp;

import com.gangzi.onedaybest.api.WeChatOnListener;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.model.WeChatModel;
import com.gangzi.onedaybest.model.imp.WeChatMoelImp;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.ui.WeChatView;

/**
 * Created by gangzi on 2017/6/23.
 */

public class WeChatPresenterImp implements WeChatPressenter,WeChatOnListener{

    private WeChatModel mWeChatModel;
    private WeChatView mWeChatView;

    public WeChatPresenterImp(WeChatView mWeChatView) {
        this.mWeChatView=mWeChatView;
        mWeChatModel=new WeChatMoelImp(this);
    }

    @Override
    public void getWeChatData(int pno, int ps, String key, String dtype) {
        mWeChatView.showProgress();
        mWeChatModel.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void onSuccess(WeChatData s) {
        mWeChatView.hideProgress();
         mWeChatView.loadWeChat(s);
    }

    @Override
    public void onFailure(Throwable e) {
        mWeChatView.hideProgress();
    }
}
