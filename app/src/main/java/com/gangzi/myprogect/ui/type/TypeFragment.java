package com.gangzi.myprogect.ui.type;

import android.view.LayoutInflater;
import android.view.View;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TypeFragment extends BaseFragment {

    String url="http://v.juhe.cn/toutiao/index";
    String key="8fdf66756a83d0ffc3c42f3ebe8c0c7d";

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_type,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        OkHttpUtils.get().url(url).addParams("key",key).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("response---"+response);
            }
        });
    }
}
