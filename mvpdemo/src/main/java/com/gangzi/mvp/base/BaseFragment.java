package com.gangzi.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gangzi.mvp.utils.StateUtils;

/**
 * Created by dan on 2017/9/29.
 */

public abstract class BaseFragment <V,T extends BasePresenter<V>> extends Fragment{

    public T presenter;
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter=initPresenter();
        presenter.attach((V) this);
        isConnectNet();
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void isConnectNet() {
        if (!StateUtils.isNetworkAvailable(mContext)){
            Toast.makeText(mContext,"网络不见了~",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 强制子类实现
     * @return
     */
    public abstract View initView();

    // 实例化presenter
    public abstract T initPresenter();
    //  public abstract void initView();
    //  public  abstract void isConnectNet();

    public void initData(){

    }
}
