package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.myprogect.R;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.news.view.NewsActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_MORE=0;
    public static final int TYPE_LIST=1;
    public static final int TYPE_BOTTOM=2;

    public int currentType=TYPE_MORE;

    private Context mContext;
    private List<News.ResultBean.DataBean> dataBeanList;
    private final LayoutInflater mLayoutInflater;

    public HomeAdapter(Context mContext, List<News.ResultBean.DataBean> dataBeanList) {
        this.mContext=mContext;
        this.dataBeanList=dataBeanList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case TYPE_MORE:
                currentType = TYPE_MORE;
                break;
            case TYPE_LIST:
                currentType = TYPE_LIST;
                break;
            case TYPE_BOTTOM:
                currentType = TYPE_BOTTOM;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_MORE){
            View view= mLayoutInflater.inflate(R.layout.more_item,parent, false);
            return new MoreViewHolder(view);
        }else if (viewType==TYPE_LIST){
            View view1=LayoutInflater.from(mContext).inflate(R.layout.news_list_item,parent, false);
            return new ListViewHolder(view1);
        }else if (viewType==TYPE_BOTTOM){
            View view2=LayoutInflater.from(mContext).inflate(R.layout.item_bottom,parent,false);
            return new BottomViewHolder(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_MORE){
            MoreViewHolder moreHolder= (MoreViewHolder) holder;
           // setMoreItem((MoreViewHolder)holder,position);
        }else if (getItemViewType(position)==TYPE_LIST){
            ListViewHolder listViewHolder= (ListViewHolder) holder;
            listViewHolder.setNewsItem(dataBeanList);
        }else if (getItemViewType(position)==TYPE_BOTTOM){
            BottomViewHolder bottomViewHolder= (BottomViewHolder) holder;
        }
    }

    class MoreViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_more;

        public MoreViewHolder(final View itemView) {
            super(itemView);
            tv_more= (TextView) itemView.findViewById(R.id.tv_more);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NewsActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
    class ListViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView news_recycleView;

        public ListViewHolder(View itemView) {
            super(itemView);
           news_recycleView= (RecyclerView) itemView.findViewById(R.id.news_recycleView);
        }

        public void setNewsItem(List<News.ResultBean.DataBean> list) {
            NewsAdapter adapter=new NewsAdapter(mContext,list);
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            news_recycleView.setLayoutManager(manager);
            news_recycleView.setAdapter(adapter);
        }
    }
    class BottomViewHolder extends RecyclerView.ViewHolder{

        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
