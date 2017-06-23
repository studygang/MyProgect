package com.gangzi.onedaybest.model.imp;

import com.gangzi.onedaybest.api.APIManager;
import com.gangzi.onedaybest.api.WeChatOnListener;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.model.WeChatModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gangzi on 2017/6/23.
 */

public class WeChatMoelImp implements WeChatModel {

    private WeChatOnListener mWeChatOnListener;

    public WeChatMoelImp(WeChatOnListener weChatOnListener) {
        this.mWeChatOnListener = weChatOnListener;
    }

    @Override
    public void getWeChatData(int pno, int ps, String key, String dtype) {
        Observable<WeChatData> request= APIManager.getWeXinChat(pno,ps,key,dtype);
        request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeChatData>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull WeChatData weChatData) {
                mWeChatOnListener.onSuccess(weChatData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mWeChatOnListener.onFailure(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
