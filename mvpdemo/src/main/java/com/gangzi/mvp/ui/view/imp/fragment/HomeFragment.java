package com.gangzi.mvp.ui.view.imp.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.mvp.R;
import com.gangzi.mvp.base.BaseFragment;
import com.gangzi.mvp.ui.presenter.imp.HomPressenterImp;
import com.gangzi.mvp.ui.view.HomeView;

/**
 * Created by dan on 2017/9/19.
 */

public class HomeFragment extends BaseFragment<HomeView,HomPressenterImp> implements HomeView {

    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.home_fragment,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public HomPressenterImp initPresenter() {
        return new HomPressenterImp();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess() {

    }
}
