package com.gangzi.onedaybest.ui.imp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.adapter.WeChatAdapter;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.pressenter.imp.WeChatPresenterImp;
import com.gangzi.onedaybest.ui.WeChatView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatActivity extends AppCompatActivity implements WeChatView{

    @BindView(R.id.recycle_wechat)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private ClassicsHeader mClassicsHeader;


    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    private WeChatPressenter mWeChatPressenter;

    private int pno=1;
    private int currentPager;
    private int totalPager=2;

    private WeChatAdapter mWeChatAdapter;
    private List<WeChatData.ResultBean.ListBean> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        mWeChatPressenter=new WeChatPresenterImp(this,false);
        initData();
        ButterKnife.bind(this);
        mRefreshLayout.setEnableHeaderTranslationContent(true);
       /* int deta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader = (ClassicsHeader)mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis()-deta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));*/
       // mClassicsHeader.setSpinnerStyle(SpinnerStyle.FixedBehind);
      //  mRefreshLayout.setPrimaryColors(0xff444444, 0xffffffff);

        //mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                  refreshData();
                  refreshlayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pno<=3){
                    loadMoreData();
                    refreshlayout.finishLoadmore();
                }else{
                   // mRefreshLayout.setEnableLoadmore(false);
                    Toast.makeText(WeChatActivity.this,"没有更多数据了！",Toast.LENGTH_SHORT).show();
                    refreshlayout.finishLoadmore();
                }
            }
        });
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadWeChat(WeChatData weChatData) {
        data=weChatData.getResult().getList();
        mWeChatAdapter=new WeChatAdapter(this,data);
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
