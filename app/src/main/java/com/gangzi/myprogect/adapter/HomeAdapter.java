package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BANNER=0;
    private static final int TYPE_MORE=1;
    private static final int TYPE_LIST=2;
    private static final int TYPE_FOOT=3;

    private Context mContext;

    public HomeAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
       if (position==0){
           return TYPE_BANNER;
       }else if (position==1){
           return  TYPE_MORE;
       }else if (position==2){
           return TYPE_LIST;
       }else {
           return TYPE_FOOT;
       }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
