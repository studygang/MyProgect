package com.gangzi.onedaybest.ui;

import com.gangzi.onedaybest.bean.WeChatData;

/**
 * Created by gangzi on 2017/6/23.
 */

public interface WeChatView {
    void showProgress();
    void hideProgress();
    void loadWeChat(WeChatData weChatData);
}
