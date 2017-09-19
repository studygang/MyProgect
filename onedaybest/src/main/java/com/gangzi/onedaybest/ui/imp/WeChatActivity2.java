package com.gangzi.onedaybest.ui.imp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.adapter.WeChatAdapter2;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.pressenter.imp.WeChatPresenterImp;
import com.gangzi.onedaybest.ui.WeChatView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatActivity2 extends AppCompatActivity implements WeChatView{

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayouts)
    RefreshLayout mRefreshLayout;

    public boolean isLoading;

    private ClassicsHeader mClassicsHeader;


    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    private WeChatPressenter mWeChatPressenter;

    private int pno=1;
    private int currentPager;
    private int totalPager=2;

    private WeChatAdapter2 mWeChatAdapter;
    private List<WeChatData.ResultBean.ListBean> data;
    private SpinKitView spinKitView;
    private Sprite drawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat2);
        spinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        drawable =  new Circle();
        mWeChatPressenter=new WeChatPresenterImp(this,false);
        initData();
        ButterKnife.bind(this);
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        mRefreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setLoadmoreFinished(false);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pno<=3){
                    loadMoreData();
                    mRefreshLayout.finishLoadmore();
                }else{
                   // mRefreshLayout.setEnableLoadmore(false);
                    //footer.setVisibility(View.GONE);
                    mRefreshLayout.finishLoadmore();
                    Toast.makeText(WeChatActivity2.this,"没有更多数据了！",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                }
            }
        });
        //触发自动刷新
      //  mRefreshLayout.autoRefresh();
    }

    private void initData() {
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }

    private void refreshData(){
        pno=1;
        mWeChatPressenter.refreshWechatData(pno,ps,key,dtype,true);
    }

    private void loadMoreData(){
        pno++;
        mWeChatPressenter.loadMoreWechatData(pno,ps,key,dtype,true);
    }

    @Override
    public void showProgress() {
        spinKitView.setIndeterminateDrawable(drawable);
    }

    @Override
    public void hideProgress() {
       spinKitView.setVisibility(View.GONE);

        //spinKitView.setIndeterminateDrawable(null);
    }

    @Override
    public void loadWeChat(WeChatData weChatData) {
        data=weChatData.getResult().getList();
        mWeChatAdapter=new WeChatAdapter2(this,data);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mWeChatAdapter);
    }

    @Override
    public void refreshData(WeChatData weChatData) {
        data=weChatData.getResult().getList();
        mWeChatAdapter.clear();
        mWeChatAdapter.addData(0,data);
    }

    @Override
    public void loadMoreData(WeChatData weChatData) {
        data=weChatData.getResult().getList();
        mWeChatAdapter.addData(mWeChatAdapter.getcountData(),data);
    }
}
