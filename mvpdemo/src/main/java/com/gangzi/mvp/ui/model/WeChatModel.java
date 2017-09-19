package com.gangzi.mvp.ui.model;

/**
 * Created by gangzi on 2017/6/23.
 */

public interface WeChatModel {
    void getWeChatData(int pno, int ps, String key, String dtype);
    void refreshWechatData(int pno, int ps, String key, String dtype, boolean isRefreshData);
    void loadMoreWechatData(int pno, int ps, String key, String dtype, boolean isLoadMore);
}
