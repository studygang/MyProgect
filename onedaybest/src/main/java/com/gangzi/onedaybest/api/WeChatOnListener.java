package com.gangzi.onedaybest.api;

import com.gangzi.onedaybest.bean.WeChatData;

/**
 * Created by gangzi on 2017/6/23.
 */

public interface WeChatOnListener {
    void onSuccess(WeChatData s);
    void onFailure(Throwable e);
    void refresh(WeChatData s);
    void loadMore(WeChatData s);
}
