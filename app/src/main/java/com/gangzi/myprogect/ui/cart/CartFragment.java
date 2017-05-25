package com.gangzi.myprogect.ui.cart;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/25.
 */

public class CartFragment extends BaseFragment{
    @Override
    public View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_cart,null);
        return view;
    }
}
