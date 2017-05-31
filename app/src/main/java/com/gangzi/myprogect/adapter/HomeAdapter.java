package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.entity.News;
import com.gangzi.myprogect.ui.news.view.imp.NewsActivity;
import com.gangzi.myprogect.ui.news.view.imp.NewsDetailActivity;

import java.util.List;
import java.util.Map;

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
            View view= mLayoutInflater.inflate(R.layout.more_item,null);
            return new MoreViewHolder(view,mContext);
        }else if (viewType==TYPE_LIST){
            View view1=mLayoutInflater.inflate(R.layout.news_list_item,null);
            return new ListViewHolder(view1,mContext);
        }else if (viewType==TYPE_BOTTOM){
            View view2=mLayoutInflater.inflate(R.layout.item_bottom,null);
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
        private Context mContext;

        public MoreViewHolder(final View itemView, Context context) {
            super(itemView);
            tv_more= (TextView) itemView.findViewById(R.id.tv_more);
            this.mContext=context;
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
        private Context mContext;

        public ListViewHolder(View itemView, Context context) {
            super(itemView);
            news_recycleView= (RecyclerView) itemView.findViewById(R.id.news_recycleView);
            this.mContext=context;
        }

        public void setNewsItem(final List<News.ResultBean.DataBean> list) {
            NewsAdapter adapter=new NewsAdapter(mContext,list);
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            news_recycleView.setLayoutManager(manager);
            news_recycleView.setAdapter(adapter);
            adapter.setOnItemClickListener(new NewsAdapter.onItemClickListener() {
                @Override
                public void onItemClickListener(int position, Map<String, String> map) {
                    News.ResultBean.DataBean dataBean=list.get(position);
                    Intent intent=new Intent();
                    String uniquekey=dataBean.getUniquekey();
                    String title=dataBean.getTitle();
                    String category=dataBean.getCategory();
                    String url=dataBean.getUrl();
                    String author_name=dataBean.getAuthor_name();

                    System.out.println("--------homeAdapterUrl---"+url);

                    intent.putExtra("uniquekey",uniquekey);
                    intent.putExtra("title",title);
                    intent.putExtra("category",category);
                    intent.putExtra("url",url);
                    intent.putExtra("author_name",author_name);
                    intent.setClass(mContext,NewsDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
          /*  adapter.setOnItemClickListener(new NewsAdapter.onItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position, Map<String, String> map) {
                    Intent intent=new Intent();
                    String uniquekey=map.get("uniquekey");
                    String title=map.get("title");
                    String category=map.get("category");
                    String url=map.get("url");
                    String author_name=map.get("author_name");
                    intent.putExtra("uniquekey",uniquekey);
                    intent.putExtra("title",title);
                    intent.putExtra("category",category);
                    intent.putExtra("url",url);
                    intent.putExtra("author_name",author_name);
                    intent.setClass(mContext,NewsDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });*/
        }
    }
    class BottomViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;

        public BottomViewHolder(View itemView) {
            super(itemView);
            //this.mContext=context;
        }
    }
}
