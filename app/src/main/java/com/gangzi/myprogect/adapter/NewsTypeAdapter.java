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

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NewsTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News.ResultBean.DataBean> dataBeanList;
    private Context mContext;
    private  LayoutInflater mInflater;

    private static final int TYPE_LIST=0;
    private static final int TYPE_BOTTOM=1;

    public NewsTypeAdapter(Context context, List<News.ResultBean.DataBean> dataBeanList) {
        this.mContext=context;
        this.dataBeanList = dataBeanList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_LIST){
            return new MovieViewHolder(mInflater.inflate(R.layout.news_item,parent,false));
        }else if (viewType==TYPE_BOTTOM){
            return new BottomViewHolder(mInflater.inflate(R.layout.item_bottom,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder){
            setListData((MovieViewHolder)holder,position);
        }
    }

    private void setListData(MovieViewHolder holder, final int position) {
        News.ResultBean.DataBean data=dataBeanList.get(position);
        String uniquekey=data.getUniquekey();
        String title=data.getTitle();
        String date=data.getDate();
        String category=data.getCategory();
        String author_name=data.getAuthor_name();
        String url=data.getUrl();
        String imageUrl=data.getThumbnail_pic_s();
        String imageUrl2=data.getThumbnail_pic_s02();
        String imageUrl3=data.getThumbnail_pic_s03();

        holder.tv_tile.setText(title);
        holder.tv_time.setText(date);
        holder.tv_author.setText(author_name);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemOnClickListener!=null){
                    onItemOnClickListener.onClick(position);
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
    public int getItemViewType(int position) {
        if (position==dataBeanList.size()){
            return TYPE_BOTTOM;
        }else{
            return TYPE_LIST;
        }
    }



    @Override
    public int getItemCount() {
        return dataBeanList.size()+1;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_tile,tv_time,tv_author;
        private ImageView iv_image,iv_image2,iv_image3;
        private LinearLayout mLinearLayout;

        public MovieViewHolder(View itemView) {
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
    class BottomViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;

        public BottomViewHolder(View itemView) {
            super(itemView);
            //this.mContext=context;
        }
    }

    public interface onItemOnClickListener{
        void onClick(int position);
    }

    public void setOnItemOnClickListener(onItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }

    private onItemOnClickListener onItemOnClickListener;
}
