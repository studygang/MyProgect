package com.gangzi.myprogect.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    private boolean isFragmentVisible;
    private boolean isFirst;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //可见，但是并没有加载过
       /* if (isFragmentVisible&&!isFirst){
            onFragmentChange(true);
        }*/
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 强制子类实现
     * @return
     */
    public abstract View initView();

    public void initData() {
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isFragmentVisible=true;
        }
        if (initView()==null){
            return;
        }
        *//**
         * 可见，并且没有加载过
         *//*
        if (isFragmentVisible&&!isFirst){
            isFirst=true;
            onFragmentChange(true);
            return;
        }
        *//**
         * 由可见->不可见，加载过
         *//*
        if (isFragmentVisible){
            onFragmentChange(false);
            isFragmentVisible=false;
            return;
        }
    }

    *//**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     *//*
    public void onFragmentChange(boolean isVisible){

    }*/
}
