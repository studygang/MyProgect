package com.gangzi.myprogect.ui.news.view.imp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.adapter.NewsTypeAdapter;
import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.news.presenter.NewsTypeListPressenter;
import com.gangzi.myprogect.ui.news.presenter.imp.NewsTypeListPressenterImp;
import com.gangzi.myprogect.ui.news.view.NewsTypeListView;
import com.gangzi.myprogect.utils.MyProgressDialog;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/27.
 */

public class NewsFragment extends BaseFragment implements NewsTypeListView,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private String type;
    @BindView(R.id.news_list_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.news_list_swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    private NewsTypeListPressenter mNewsTypeListPressenter;
    private NewsTypeAdapter mNewsTypeAdapter;

    String url="http://v.juhe.cn/toutiao/index";
    String key="8fdf66756a83d0ffc3c42f3ebe8c0c7d";

    private MyProgressDialog mDialog;

    @Override
    public View initView() {
        View view=View.inflate(mContext,R.layout.news_fragment,null);
        ButterKnife.bind(this,view);
        mDialog=new MyProgressDialog(getActivity(),"正在加载中...");
        mDialog.show();
        mNewsTypeListPressenter=new NewsTypeListPressenterImp(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_red_light);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        if (getArguments()!=null){
            type=getArguments().getString("type");
            System.out.println("---------type----"+type);
        }
        mNewsTypeListPressenter.requestNewsTypeListData(type,url,key);
    }

    @Override
    public void returnNews(String result) {
        System.out.println("-----------------------NewsTypeListData---"+result);
        Gson gson=new Gson();
        News news= gson.fromJson(result,News.class);
        final List<News.ResultBean.DataBean> dataBeanList=news.getResult().getData();
        if (dataBeanList!=null&&dataBeanList.size()>0){
            mDialog.dismiss();
        }
        mNewsTypeAdapter=new NewsTypeAdapter(mContext,dataBeanList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mNewsTypeAdapter);
        mNewsTypeAdapter.setOnItemOnClickListener(new NewsTypeAdapter.onItemOnClickListener() {
            @Override
            public void onClick(int position) {
                News.ResultBean.DataBean dataBean=dataBeanList.get(position);
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
        Toast.makeText(getActivity(),"已经是最新新闻了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }
}
