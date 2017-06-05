package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class WelcomeAdapter extends PagerAdapter{

    private Context mContext;
   // private int[] welcomePic;
    private  List<ImageView> welcomePic;

    public WelcomeAdapter(Context context, List<ImageView> welcomePic) {
       this.mContext=context;
        this.welcomePic=welcomePic;
    }

    @Override
    public int getCount() {
        return welcomePic.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(welcomePic.get(position));
        return welcomePic.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(welcomePic.get(position));
       // super.destroyItem(container, position, object);
    }
}
