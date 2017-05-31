package com.gangzi.myprogect.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.myprogect.MainActivity;
import com.gangzi.myprogect.R;
import com.gangzi.myprogect.adapter.HomeAdapter2;
import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.home.presenter.HomeNewsPresenter;
import com.gangzi.myprogect.ui.home.presenter.imp.HomeNewsPresenterImp;
import com.gangzi.myprogect.ui.home.view.HomeNewsView;
import com.gangzi.myprogect.ui.news.view.imp.NewsDetailActivity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeFragment extends BaseFragment implements HomeNewsView,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swipRefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.float_button)
    FloatingActionButton mFloatingActionButton;

    private HomeNewsPresenter mHomeNewsPresenter;
    String url="http://v.juhe.cn/toutiao/index";
    String key="8fdf66756a83d0ffc3c42f3ebe8c0c7d";

    //private HomeAdapter mHomeAdapter;
    private HomeAdapter2 mHomeAdapter;
    DrawerLayout drawerLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            MainActivity activity= (MainActivity) context;
            drawerLayout= (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        }
    }

    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,view);
        mToolbar= (Toolbar) view.findViewById(R.id.toolBar);
        mFloatingActionButton.setOnClickListener(this);
        setToolBar();
        mHomeNewsPresenter=new HomeNewsPresenterImp(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_red_light);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mHomeNewsPresenter.RequestNews(url,key);
    }

    /**
     * 设置toolBar
     */
    private void setToolBar() {
        mToolbar.setTitle("首页");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);//设置菜单按钮可用
       // actionBar.setDisplayHomeAsUpEnabled(true);//设置回退按钮可用
        //将drawlayout与toolbar绑定在一起
       ActionBarDrawerToggle abdt=new ActionBarDrawerToggle(getActivity(),drawerLayout,mToolbar,R.string.app_name,R.string.app_name);
        abdt.syncState();//初始化状态
        //设置drawlayout的监听事件 打开/关闭
        drawerLayout.setDrawerListener(abdt);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_button:
                //FabSrcollBean fabSrcollBean=new FabSrcollBean();
                //fabSrcollBean.setTop(true);
                mRecyclerView.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void returnNews(String result) {
        System.out.println("result=="+result);
        Gson gson=new Gson();
       News news= gson.fromJson(result,News.class);
        final List<News.ResultBean.DataBean> dataBeanList=news.getResult().getData();
       // List<News.ResultBean.DataBean>dataBeanList=resultBean.getData();
       System.out.println("---dataBeanList----------"+dataBeanList.size());


        mHomeAdapter=new HomeAdapter2(mContext,dataBeanList);
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        //mRecyclerView.smoothScrollToPosition(0);
        mRecyclerView.setLayoutManager(manager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemOnClickListener(new HomeAdapter2.onItemOnClickListener() {
            @Override
            public void onClick(int position) {
                News.ResultBean.DataBean dataBean=dataBeanList.get(position-1);
                Intent intent=new Intent();
                String uniquekey=dataBean.getUniquekey();
                String title=dataBean.getTitle();
                String category=dataBean.getCategory();
                String url=dataBean.getUrl();
                String author_name=dataBean.getAuthor_name();

                System.out.println("--------homeAdapterUrl---"+url);

                intent.putExtra("uniquekey",uniquekey);
                intent.putExtra("title",title);
                intent.putExtra("category",category);
                intent.putExtra("url",url);
                intent.putExtra("author_name",author_name);
                intent.setClass(mContext,NewsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        initData();
        mRefreshLayout.setRefreshing(false);
    }
}
