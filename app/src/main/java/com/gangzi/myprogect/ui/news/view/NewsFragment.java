package com.gangzi.myprogect.ui.news.view;

import android.view.View;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/27.
 */

public class NewsFragment extends BaseFragment {
    @Override
    public View initView() {
        View view=View.inflate(mContext,R.layout.news_fragment,null);
        return view;
    }
}
