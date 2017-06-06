package com.gangzi.myprogect.ui.type.view.imp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.adapter.NewsTypeAdapter;
import com.gangzi.myprogect.adapter.TypeLeftListAdapter;
import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.news.view.imp.NewsDetailActivity;
import com.gangzi.myprogect.ui.type.presenter.NewsTypePresenter;
import com.gangzi.myprogect.ui.type.presenter.imp.NewsTypePresenterImp;
import com.gangzi.myprogect.ui.type.view.NewsTypeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TypeFragment extends BaseFragment implements NewsTypeView,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.list_left)
    ListView mListView;
    @BindView(R.id.rv_right)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_toobar)
    Toolbar mToolbar;
    @BindView(R.id.rv_right_swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    String url="http://v.juhe.cn/toutiao/index";
    String key="8fdf66756a83d0ffc3c42f3ebe8c0c7d";

    private TypeLeftListAdapter mListAdapter;
    private NewsTypePresenter mPresenter;
    private NewsTypeAdapter mNewsTypeAdapter;

    private String[] mTypesChinese = {"头条", "社会", "国内","国际","娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] pinyin={"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_type,null);
        ButterKnife.bind(this,view);
        mPresenter=new NewsTypePresenterImp(this);
        setToolBar();
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_red_light);
        mListAdapter=new TypeLeftListAdapter(mContext,mTypesChinese,pinyin);
        mListView.setAdapter(mListAdapter);
        mPresenter.requestNews(url,pinyin[0],key);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                System.out.println("-------type----"+pinyin[i]);
                mListAdapter.changeSelected(i);
                mPresenter.requestNews(url,pinyin[i],key);
                mListAdapter.notifyDataSetChanged();
            }
        });
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view1, int i, long l) {
                mListAdapter.changeSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> view) {

            }
        });
    }
    /**
     * 设置toolBar
     */
    private void setToolBar() {
        mToolbar.setTitle("分类");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);//设置菜单按钮可用
        // actionBar.setDisplayHomeAsUpEnabled(true);//设置回退按钮可用
        //将drawlayout与toolbar绑定在一起
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void returnData(String result) {
        Gson gson=new Gson();
        News news= gson.fromJson(result,News.class);
        final List<News.ResultBean.DataBean> dataBeanList=news.getResult().getData();
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
}
