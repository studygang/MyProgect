package com.gangzi.myprogect.ui.news.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.adapter.NewsViewPagerAdapter;
import com.gangzi.myprogect.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.toobars)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fabutton)
    FloatingActionButton mFloatingActionButton;

    private String[] mTypesChinese = {"头条", "社会", "国内","国际","娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] pinyin={"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};

    private List<BaseFragment> newsFragmentList;
    private NewsViewPagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setToolBar();
        setTab();
    }

    private void setToolBar() {
        mToolbar.setTitle("新闻");
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 设置Tab
     */
    private void setTab() {
        newsFragmentList=new ArrayList<>();
        for (int i=0;i<mTypesChinese.length;i++){
            newsFragmentList.add(createFragment(i));
        }
        if (fragmentAdapter==null){
            fragmentAdapter=new NewsViewPagerAdapter(getSupportFragmentManager(),newsFragmentList, Arrays.asList(mTypesChinese));
        }else{
            fragmentAdapter.refreshFragment(getSupportFragmentManager(),newsFragmentList,Arrays.asList(mTypesChinese));
        }
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.black));
    }

    private BaseFragment createFragment(int i) {
        NewsFragment fragment=new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",pinyin[i]);
        fragment.setArguments(bundle);
        return fragment;
    }
}
