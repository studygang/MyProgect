package com.gangzi.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gangzi.mvp.utils.StateUtils;

/**
 * Created by dan on 2017/9/18.
 */

public abstract  class BaseActivity <V,T extends BasePresenter<V>> extends AppCompatActivity{

    public T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // initView();
        presenter=initPresenter();
        presenter.attach((V) this);
        isConnectNet();
    }

    private void isConnectNet() {
        if (!StateUtils.isNetworkAvailable(this)){
            Toast.makeText(this,"网络不见了~",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
       // isConnectNet();
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    // 实例化presenter
    public abstract T initPresenter();
  //  public abstract void initView();
  //  public  abstract void isConnectNet();

    public void initData(){

    }

}
