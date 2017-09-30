package com.gangzi.mvp.ui.view.imp.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.mvp.R;
import com.gangzi.mvp.base.BaseFragment;
import com.gangzi.mvp.ui.presenter.imp.UserPressenterImp;
import com.gangzi.mvp.ui.view.UserView;

/**
 * Created by dan on 2017/9/29.
 */

public class UserFragment extends BaseFragment<UserView,UserPressenterImp> implements UserView {

    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.user_fragment,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public UserPressenterImp initPresenter() {
        return new UserPressenterImp();
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
