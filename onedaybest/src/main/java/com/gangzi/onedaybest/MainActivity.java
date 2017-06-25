package com.gangzi.onedaybest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gangzi.onedaybest.adapter.CenterListAdapter2;
import com.gangzi.onedaybest.adapter.MainAdapter;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.pressenter.imp.WeChatPresenterImp;
import com.gangzi.onedaybest.ui.WeChatView;
import com.gangzi.onedaybest.widget.MyProgressDialog;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WeChatView{

    @BindView(R.id.toobal)
    Toolbar mToolbar;
    @BindView(R.id.swip_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private MyProgressDialog mProgressDialog;
    private WeChatPressenter mWeChatPressenter;
    //private MainAdapter adapter;

    private int pno=1;
    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog=new MyProgressDialog(this,"正在加载中...");
        mWeChatPressenter=new WeChatPresenterImp(this);
        initData();
        ButterKnife.bind(this);
    }

    private void initData() {
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void loadWeChat(WeChatData weChatData) {
        String result=new Gson().toJson(weChatData);
        System.out.println("每日精选的数据为："+result);
        List<WeChatData.ResultBean.ListBean> data=weChatData.getResult().getList();
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        MainAdapter adapter=new MainAdapter(this,data);
        //CenterListAdapter2 adapter=new CenterListAdapter2(this,data);
        mRecyclerView.setAdapter(adapter);
    }
}
