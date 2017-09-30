package com.gangzi.mvp.ui.view.imp.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.mvp.R;
import com.gangzi.mvp.base.BaseFragment;
import com.gangzi.mvp.ui.presenter.imp.TypePressenterImp;
import com.gangzi.mvp.ui.view.TypeView;

/**
 * Created by dan on 2017/9/29.
 */

public class TypeFragment extends BaseFragment<TypeView,TypePressenterImp>implements TypeView {

    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.type_fragment,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public TypePressenterImp initPresenter() {
        return new TypePressenterImp();
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
