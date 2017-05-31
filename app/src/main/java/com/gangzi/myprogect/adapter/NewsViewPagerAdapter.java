package com.gangzi.myprogect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment>fragmentList=new ArrayList<>();
    private List<String> mTitle;
    
    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public NewsViewPagerAdapter(FragmentManager manager, List<BaseFragment> list, List<String> strings) {
        super(manager);
        this.fragmentList=list;
        this.mTitle=strings;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !CollectionUtils.isNullOrEmpty(mTitle)?mTitle.get(position):"";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void refreshFragment(FragmentManager manager, List<BaseFragment> list, List<String> strings) {
        this.mTitle=strings;
        if (this.fragmentList!=null){
            FragmentTransaction ft=manager.beginTransaction();
            for (Fragment fragment:this.fragmentList){
                ft.remove(fragment);
            }
            ft.commitAllowingStateLoss();
            ft=null;
            manager.executePendingTransactions();
        }
        this.fragmentList=list;
        notifyDataSetChanged();
    }
}
