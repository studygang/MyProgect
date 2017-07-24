package com.gangzi.onedaybest.pressenter;

/**
 * Created by gangzi on 2017/6/23.
 */

public interface WeChatPressenter {
    void getWeChatData(int pno,int ps,String key,String dtype);
    void refreshWechatData(int pno, int ps, String key, String dtype,boolean isRefreshData);
    void loadMoreWechatData(int pno, int ps, String key, String dtype,boolean isLoadMore);
}
