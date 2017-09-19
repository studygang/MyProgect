package com.gangzi.mvp.ui.presenter.imp;

import com.gangzi.mvp.base.BasePresenter;
import com.gangzi.mvp.bean.WeChatData;
import com.gangzi.mvp.interfaces.WeChatOnListener;
import com.gangzi.mvp.ui.model.WeChatModel;
import com.gangzi.mvp.ui.model.imp.WeChatMoelImp;
import com.gangzi.mvp.ui.presenter.WeChatPressenter;
import com.gangzi.mvp.ui.view.WeChatView;

/**
 * Created by dan on 2017/9/18.
 */

public class WeChatPresenterImp extends BasePresenter<WeChatView> implements WeChatPressenter,WeChatOnListener{

    private WeChatView mWeChatView;
    private WeChatModel mWeChatModel;

    public WeChatPresenterImp(WeChatView weChatView) {
        this.mWeChatView=weChatView;
        mWeChatModel=new WeChatMoelImp(this);
    }

    @Override
    public void getWeChatData(int pno, int ps, String key, String dtype) {
        mWeChatView.showLoading();
        mWeChatModel.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void refreshWechatData(int pno, int ps, String key, String dtype, boolean isRefreshData) {

    }

    @Override
    public void loadMoreWechatData(int pno, int ps, String key, String dtype, boolean isLoadMore) {

    }

    @Override
    public void onSuccess(WeChatData s) {
        mWeChatView.hideLoading();
        mWeChatView.loadWeChat(s);
    }

    @Override
    public void onFailure(Throwable e) {
        mWeChatView.hideLoading();
        mWeChatView.showError();
    }

    @Override
    public void refresh(WeChatData s) {

    }

    @Override
    public void loadMore(WeChatData s) {

    }
}
