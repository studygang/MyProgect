package com.gangzi.mvp.ui.presenter;

/**
 * Created by dan on 2017/9/18.
 */

public interface WeChatPressenter {
    void getWeChatData(int pno,int ps,String key,String dtype);
    void refreshWechatData(int pno, int ps, String key, String dtype,boolean isRefreshData);
    void loadMoreWechatData(int pno, int ps, String key, String dtype,boolean isLoadMore);
}
