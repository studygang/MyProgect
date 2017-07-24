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
    private boolean isLoadMoreData;

    public WeChatPresenterImp(WeChatView mWeChatView,boolean isLoadMoreData) {
        this.mWeChatView=mWeChatView;
        this.isLoadMoreData=isLoadMoreData;
        mWeChatModel=new WeChatMoelImp(this);
    }

    @Override
    public void getWeChatData(int pno, int ps, String key, String dtype) {
        mWeChatView.showProgress();
        mWeChatModel.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void refreshWechatData(int pno, int ps, String key, String dtype,boolean isRefreshData) {
        mWeChatModel.refreshWechatData(pno,ps,key,dtype,isRefreshData);
    }

    @Override
    public void loadMoreWechatData(int pno, int ps, String key, String dtype,boolean isLoadMoreData) {
        mWeChatModel.loadMoreWechatData(pno,ps,key,dtype,isLoadMoreData);
    }

    @Override
    public void onSuccess(WeChatData s) {
        mWeChatView.hideProgress();
         mWeChatView.loadWeChat(s);
        /*if (isLoadMoreData){
            mWeChatView.loadMoreData(s);
        }else{
            mWeChatView.refreshData(s);
        }*/
    }

    @Override
    public void onFailure(Throwable e) {
        mWeChatView.hideProgress();
    }

    @Override
    public void refresh(WeChatData s) {
        mWeChatView.refreshData(s);
    }

    @Override
    public void loadMore(WeChatData s) {
        mWeChatView.loadMoreData(s);
    }
}
