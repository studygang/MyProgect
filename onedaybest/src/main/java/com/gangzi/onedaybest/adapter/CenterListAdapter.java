package com.gangzi.onedaybest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.bean.WeChatData;

import java.util.List;

/**
 * Created by gangzi on 2017/6/23.
 */

public class CenterListAdapter extends RecyclerView.Adapter<CenterListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    List<WeChatData.ResultBean.ListBean> dataListcenter;

    public CenterListAdapter(Context context, List<WeChatData.ResultBean.ListBean> dataList) {
        mContext = context;
        mInflater=LayoutInflater.from(mContext);
        this.dataListcenter=dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ViewHolder((mInflater.inflate(R.layout.item_center_list,null)),mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String from=dataListcenter.get(position).getSource();
        String title=dataListcenter.get(position).getTitle();
        String url=dataListcenter.get(position).getUrl();
        String firstImageUrl=dataListcenter.get(position).getFirstImg();
        holder.tv_title.setText(title);
        holder.tv_author.setText(from);
        Glide.with(mContext).load(firstImageUrl).into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return dataListcenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView tv_title,tv_author;
        private ImageView iv_image;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mContext=context;
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image1);
        }
    }
}
