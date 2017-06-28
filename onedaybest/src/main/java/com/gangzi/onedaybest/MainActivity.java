package com.gangzi.onedaybest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gangzi.onedaybest.adapter.CenterListAdapter2;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.pressenter.imp.WeChatPresenterImp;
import com.gangzi.onedaybest.ui.WeChatView;
import com.gangzi.onedaybest.widget.MyProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WeChatView{

    @BindView(R.id.toobal)
    Toolbar mToolbar;
    @BindView(R.id.swip_refresh)
    MaterialRefreshLayout mRefreshLayout;
    //SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private MyProgressDialog mProgressDialog;
    private WeChatPressenter mWeChatPressenter;
    //private MainAdapter adapter;
    private CenterListAdapter2 adapter;
    //private TestAdapter adapter;
    //private CenterListAdapter4 mCenterListAdapter4;
    private int lastVisibleItem;
    private  boolean isLoading = false;
    private  List<WeChatData.ResultBean.ListBean>tempdataList;

    private int pno=1;
    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    private static final int NORMAL=0;
    private static final int REFRESH=1;
    private static final int LOADING=2;

    private int status=NORMAL;

    private int currentPager;
    private int totalPager=2;
    private List<WeChatData.ResultBean.ListBean>data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog=new MyProgressDialog(this,"正在加载中...");
        mWeChatPressenter=new WeChatPresenterImp(this);
        initData();
        ButterKnife.bind(this);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                if (pno<totalPager){
                    loadData();
                }else{
                    //mRefreshLayout.setLoadMore(false);
                    Toast.makeText(MainActivity.this,"已经到底部了", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
        //mRefreshLayout.autoRefresh();
        //mRefreshLayout.autoRefreshLoadMore();
       /* mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status=REFRESH;
                pno=1;
                mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
            }
        });*/
       /* mRecyclerView.setOnRefreshListener(new XRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                refreshData();
            }

            @Override
            public void onLoadMore() {
                //加载更多
                if (pno<totalPager){
                    loadData();
                }else{
                    //mRefreshLayout.setLoadMore(false);
                    Toast.makeText(MainActivity.this,"已经到底部了", Toast.LENGTH_SHORT).show();
                   // mRefreshLayout.finishRefreshLoadMore();
                    mRecyclerView.refreshComlete();
                }
            }
        });*/
    }

    private void initData() {
        //status=NORMAL;
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }

    private void refreshData(){
        pno=1;
        status=REFRESH;
        initData();
    }
    private void loadData(){
        pno=pno+1;
        status=LOADING;
        initData();
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
        data=weChatData.getResult().getList();
       /* String result=new Gson().toJson(weChatData);

        if (status==REFRESH){
            tempdataList=weChatData.getResult().getList();
            System.out.println("下拉刷新的每日精选的数据为："+result);
        }else if (status==LOADING){
            System.out.println("上拉加载更多的每日精选的数据为："+result);
            tempdataList=weChatData.getResult().getList();
        }else if (status==NORMAL){
            tempdataList=weChatData.getResult().getList();
            System.out.println("正常的每日精选的数据为："+result);
        }*/
       // totalPager=weChatData.getResult().getTotalPage();
      //  System.out.println("总页数为："+totalPager);
       // data=weChatData.getResult().getList();
       // MainAdapter adapter=new MainAdapter(this,data);
       //TestAdapter adapter1=new TestAdapter(this,data);
        //mCenterListAdapter4=new CenterListAdapter4(this,tempdataList);
        //adapter=new CenterListAdapter2(this,tempdataList);
        // manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
       // mRecyclerView.setLayoutManager(manager);
       // switch (status){
          //  case NORMAL:
             /*   LinearLayoutManager manager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                mRecyclerView.setLayoutManager(manager2);
                MainAdapter adapter2=new MainAdapter(this,data);
                //CenterListAdapter2 adapter=new CenterListAdapter2(this,data);
                mRecyclerView.setAdapter(adapter2);*/
                /*CenterListAdapter2 adapter1=new CenterListAdapter2(this,data);
                LinearLayoutManager manager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                mRecyclerView.setLayoutManager(manager1);
                mRecyclerView.setAdapter(adapter1);*/
                //break;
           // case REFRESH:

                //adapter.cleanData();
                //adapter.addData(0,data);
                //adapterTest.setNewData(data);
               // mRefreshLayout.finishRefresh();
                //adapter.setNewData(tempdataList);
               // mRefreshLayout.finishRefresh();
                //mRefreshLayout.setRefreshing(false);
                //System.out.println("下拉刷新完mCenterListAdapter4.getItemCount()---="+mCenterListAdapter4.getItemCount());
               // isLoading=false;
               /* MainAdapter adapter3=new MainAdapter(this,data);
                //adapter3.cleanData(data);
                mRefreshLayout.finishRefresh();
                LinearLayoutManager manager3=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                mRecyclerView.setLayoutManager(manager3);
                //CenterListAdapter2 adapter=new CenterListAdapter2(this,data);
                mRecyclerView.setAdapter(adapter3);*/
              //  break;
            //case LOADING:
                //adapter.addData(adapter.getcountData(),data);
                // adapter.setLoadMoreData(tempdataList);
                //isLoading=false;
               //  mRefreshLayout.finishRefreshLoadMore();
                /*if (pno<=totalPager){
                    adapter.setBottomView(true);
                }else{
                    adapter.setBottomView(false);
                }
                break;
        }
        mRecyclerView.setAdapter(adapter);
*/
       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==mCenterListAdapter4.getItemCount()&&!isLoading){
                    System.out.println("onScrollStateChanged--lastVisible---="+lastVisibleItem);
                    System.out.println("onScrollStateChanged----mCenterListAdapter4.getItemCount()---="+mCenterListAdapter4.getItemCount());
                    if (pno<totalPager){
                        status=LOADING;
                        isLoading=true;
                        pno+=1;
                        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
                        //mCenterListAdapter4.setBottomView(true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem=manager.findLastVisibleItemPosition();
                System.out.println("onScrolled----lastVisibleItem--===-----"+lastVisibleItem);
            }
        });*/
      /*  LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);*/
      showData(data);
    }

    private void showData(List<WeChatData.ResultBean.ListBean> data) {
        switch (status){
            case NORMAL:
                adapter=new CenterListAdapter2(this,data);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager=new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(manager);
                break;
            case REFRESH:
                adapter.cleanData();
                adapter.addData(data);
              //  mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
               // mRecyclerView.refreshComlete();
                break;
            case LOADING:
                adapter.addData(adapter.getcountData(), data);
              //  mRecyclerView.scrollToPosition(adapter.getcountData());
                mRefreshLayout.finishRefreshLoadMore();
               // mRecyclerView.refreshComlete();
               if (pno<=totalPager){
                    adapter.setBottomView(true);
                }else{
                    adapter.setBottomView(false);
                }
                break;
        }
    }

}
