package com.gangzi.mvp.ui.model.imp;

import com.gangzi.mvp.bean.WeChatData;
import com.gangzi.mvp.interfaces.NetRequestOnListener;
import com.gangzi.mvp.ui.model.WeChatModel;
import com.gangzi.mvp.utils.OkhttpManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gangzi on 2017/6/23.
 */

public class WeChatMoelImp implements WeChatModel {

    private NetRequestOnListener<WeChatData> mWeChatOnListener;

    private boolean isLoadMore;
    private boolean isRefresh;


    public WeChatMoelImp(NetRequestOnListener<WeChatData> weChatOnListener) {
        this.mWeChatOnListener = weChatOnListener;
    }

    @Override
    public void getWeChatData(int pno, int ps, String key, String dtype) {
      /*  Observable<WeChatData> request= APIManager.getWeXinChat(pno,ps,key,dtype);
        request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeChatData>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull WeChatData weChatData) {

                if (isRefresh){
                    mWeChatOnListener.refresh(weChatData);
                    isRefresh=false;
                }else if (isLoadMore){
                    mWeChatOnListener.loadMore(weChatData);
                    isLoadMore=false;
                }else{
                    mWeChatOnListener.onSuccess(weChatData);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mWeChatOnListener.onFailure(e);
            }

            @Override
            public void onComplete() {

            }
        });*/
      String url="http://v.juhe.cn/weixin/query";
        Map<String,Object>map=new HashMap<>();
        map.put("pno",String.valueOf(pno));
        map.put("ps",String.valueOf(ps));
        map.put("key",key);
        map.put("dtype",dtype);

        OkhttpManager.getInstance().doGet(url, map, new OkhttpManager.NetworkRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                WeChatData weChatData=new Gson().fromJson(result,WeChatData.class);
                if (isRefresh){
                    mWeChatOnListener.refresh(weChatData);
                    isRefresh=false;
                }else if (isLoadMore){
                    mWeChatOnListener.loadMore(weChatData);
                    isLoadMore=false;
                }else{
                    mWeChatOnListener.onSuccess(weChatData);
                }
            }

            @Override
            public void onRequestFail(String msg) {
                mWeChatOnListener.onFail(msg);
            }
        });
    }

    @Override
    public void refreshWechatData(int pno, int ps, String key, String dtype,boolean isRefreshData) {

    }

    @Override
    public void loadMoreWechatData(int pno, int ps, String key, String dtype,boolean isLoadMoreData) {

    }
}
