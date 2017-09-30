package com.gangzi.mvp.ui.view.imp.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.mvp.R;
import com.gangzi.mvp.base.BaseFragment;
import com.gangzi.mvp.ui.presenter.imp.CartPressenterImp;
import com.gangzi.mvp.ui.view.CartView;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dan on 2017/9/29.
 */

public class CartFragment extends BaseFragment<CartView,CartPressenterImp> implements CartView{

    @BindView(R.id.recyclerView)
     SuperRecyclerView mSuperRecyclerView;



    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.cart_fragment,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public CartPressenterImp initPresenter() {
        return new CartPressenterImp();
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
