package com.gangzi.myprogect.ui.home;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.ui.home.presenter.HomeNewsPresenter;
import com.gangzi.myprogect.ui.home.presenter.imp.HomeNewsPresenterImp;
import com.gangzi.myprogect.ui.home.view.HomeNewsView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeFragment extends BaseFragment implements HomeNewsView,View.OnClickListener{

    @BindView(R.id.swipRefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    private HomeNewsPresenter mHomeNewsPresenter;
    String url="http://v.juhe.cn/toutiao/index";
    String key="8fdf66756a83d0ffc3c42f3ebe8c0c7d";


    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,view);
        mToolbar= (Toolbar) view.findViewById(R.id.toolBar);
        setToolBar();
        mHomeNewsPresenter=new HomeNewsPresenterImp(this);

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
      //  ActionBarDrawerToggle abdt=new ActionBarDrawerToggle(getActivity(),MainActivity.getDrawerLayout(),mToolbar,R.string.app_name,R.string.app_name);
      //  abdt.syncState();//初始化状态
        //设置drawlayout的监听事件 打开/关闭
       // drawerLayout.setDrawerListener(abdt);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void returnNews(String result) {
        System.out.println("result=="+result);
    }
}
