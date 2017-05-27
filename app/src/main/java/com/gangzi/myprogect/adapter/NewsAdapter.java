package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.myprogect.R;
import com.gangzi.myprogect.entity.News;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/26.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private Context mContext;
    private List<News.ResultBean.DataBean> list;
    private Map<String,String> map=new HashMap<>();

    public NewsAdapter( Context mContext,List<News.ResultBean.DataBean> list) {
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setNewsItem(holder,position);
    }

    private void setNewsItem(ViewHolder holder, final int position) {
        News.ResultBean.DataBean data=list.get(position);
        String uniquekey=data.getUniquekey();
        String title=data.getTitle();
        String date=data.getDate();
        String category=data.getCategory();
        String author_name=data.getAuthor_name();
        String url=data.getUrl();
        String imageUrl=data.getThumbnail_pic_s();
        String imageUrl2=data.getThumbnail_pic_s02();
        String imageUrl3=data.getThumbnail_pic_s03();

        map.put("uniquekey",uniquekey);
        map.put("title",title);
        map.put("category",category);
        map.put("url",url);
        map.put("author_name",author_name);

        holder.tv_tile.setText(title);
        holder.tv_time.setText(date);
        holder.tv_author.setText(author_name);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClickListener(position,map);
                }
            }
        });

        if (TextUtils.isEmpty(imageUrl)){
            return;
        }else{
            holder.iv_image.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(imageUrl).into(holder.iv_image);
        }
        if (TextUtils.isEmpty(imageUrl2)){
            return;
        }else{
            holder.iv_image2.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(imageUrl2).into(holder.iv_image2);
        }
        if (TextUtils.isEmpty(imageUrl3)){
            return;
        }else{
            holder.iv_image3.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(imageUrl3).into(holder.iv_image3);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_tile,tv_time,tv_author;
        private ImageView iv_image,iv_image2,iv_image3;
        private LinearLayout mLinearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_tile= (TextView) itemView.findViewById(R.id.tv_title);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image1);
            iv_image2= (ImageView) itemView.findViewById(R.id.iv_image2);
            iv_image3= (ImageView) itemView.findViewById(R.id.iv_image3);
            mLinearLayout= (LinearLayout) itemView.findViewById(R.id.linear);
        }

    }
    public interface onItemClickListener{
        void onItemClickListener(int position, Map<String,String> map);
    }

    public void setOnItemClickListener(NewsAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private onItemClickListener onItemClickListener;

}
