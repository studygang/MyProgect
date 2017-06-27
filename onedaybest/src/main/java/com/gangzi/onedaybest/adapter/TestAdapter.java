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
 * Created by dan on 2017/6/27.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeChatData.ResultBean.ListBean> dataLists;
    private boolean isShowBottom;

    public TestAdapter(Context context, List<WeChatData.ResultBean.ListBean> data) {
        this.mContext=context;
        mInflater=LayoutInflater.from(mContext);
        this.dataLists=data;
    }

    @Override
    public TestAdapter.TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_center_list,null);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.TestViewHolder holder, int position) {
        WeChatData.ResultBean.ListBean data=dataLists.get(position);
        String from=data.getSource();
        String title=data.getTitle();
        String url=data.getUrl();
        String firstImageUrl=data.getFirstImg();
        holder.tv_title.setText(title);
        holder.tv_author.setText(from);
        Glide.with(mContext).load(firstImageUrl).into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public void setNewData(List<WeChatData.ResultBean.ListBean> data) {
        //dataLists.clear();
        dataLists.addAll(data);
        //notifyDataSetChanged();
    }

    public void setBottomView(boolean b) {
    }

    public void setLoadMoreData(List<WeChatData.ResultBean.ListBean> data) {
        int size=dataLists.size();
        dataLists.addAll(data);
        notifyItemInserted(size);
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title,tv_author;
        private ImageView iv_image;

        public TestViewHolder(View itemView) {
            super(itemView);
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image1);
        }
    }
}
