package com.gangzi.mvp.ui.view;

import com.gangzi.mvp.base.BaseView;
import com.gangzi.mvp.bean.WeChatData;

/**
 * Created by dan on 2017/9/18.
 */

public interface WeChatView extends BaseView {

    void loadWeChat(WeChatData weChatData);
    void refreshData(WeChatData weChatData);
    void loadMoreData(WeChatData weChatData);
}
