package com.gangzi.onedaybest.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.onedaybest.message.Message;
import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.bean.WeChatData;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by gangzi on 2017/6/23.
 */

public class CenterListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<WeChatData.ResultBean.ListBean> dataList;
    private final LayoutInflater mLayoutInflater;
    private List<WeChatData.ResultBean.ListBean> tempDataList=new ArrayList<>();

    private static final int TYPE_MORE=0;
    private static final int TYPE_LIST=1;
    private static final int TYPE_BOTTOM=2;

    private boolean isShowFootView;

    public CenterListAdapter2(Context mContext, List<WeChatData.ResultBean.ListBean> listData) {
        this.mContext=mContext;
        this.dataList=listData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_MORE;
        }else if (position==dataList.size()+1){
            return TYPE_BOTTOM;
        }else{
            return TYPE_LIST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_MORE){
            return new MoreViewHolder(mLayoutInflater.inflate(R.layout.more_item,parent,false));
        }else if (viewType==TYPE_LIST){
            return new NewsViewHolder(mLayoutInflater.inflate(R.layout.item_center_list,parent,false));
        }else if (viewType==TYPE_BOTTOM){
            return new BottomViweHolder(mLayoutInflater.inflate(R.layout.item_bottom,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof NewsViewHolder){
            setNews((NewsViewHolder)holder,position);
        }else if (holder instanceof BottomViweHolder){
           changeStatus((BottomViweHolder)holder,position);
       }else if (holder instanceof MoreViewHolder){
           more((MoreViewHolder)holder,position);
       }

    }

    private void more(MoreViewHolder holder, int position) {
        holder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Message("openCamer"));
            }
        });
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
        //photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
     //   startActivityForResult(intent, 1);
    }

    private void setNews(NewsViewHolder holder, final int position) {
        WeChatData.ResultBean.ListBean data=dataList.get(position-1);
        String from=data.getSource();
        String title=data.getTitle();
        String url=data.getUrl();
        String firstImageUrl=data.getFirstImg();
        holder.tv_title.setText(title);
        holder.tv_author.setText(from);
        Glide.with(mContext).load(firstImageUrl).into(holder.iv_image);
    }

    private void changeStatus(BottomViweHolder holder, int position) {
        if (isShowFootView){
            holder.bottom.setVisibility(View.VISIBLE);
        }else{
            holder.bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size()+2;
        //return 1;
    }

    public void cleanData() {
        dataList.clear();
        notifyItemRangeChanged(0,dataList.size());
    }

    public void addData(int position, List<WeChatData.ResultBean.ListBean> datas) {
        if (datas!=null&&datas.size()>0){
            dataList.addAll(position,datas);
           // dataList=tempDataList;
           // setBottomView(isShowFootView);
            notifyItemRangeChanged(position,dataList.size());
          //  notifyItemInserted(position);
        }
    }

    public int getcountData() {
        return dataList.size();
    }

    public void setLoadMoreData(List<WeChatData.ResultBean.ListBean> data) {
       int size=dataList.size();
        dataList.addAll(size,data);
        notifyItemRangeChanged(size, dataList.size());
        //dataList.addAll(data);
        //notifyDataSetChanged();
        //notifyItemInserted(size);
    }

    public void setNewData(List<WeChatData.ResultBean.ListBean> data) {
        dataList.clear();
        dataList.addAll(data);
        //notifyDataSetChanged();
    }

    public void setBottomView(boolean b) {
        if (b){
            isShowFootView=true;
           // notifyDataSetChanged();
        }else{
            isShowFootView=false;
            //notifyDataSetChanged();
        }
    }

    public void addData(List<WeChatData.ResultBean.ListBean> data) {
      addData(0,data);
    }


    class MoreViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_more;

        public MoreViewHolder(View itemView) {
            super(itemView);
            tv_more= (TextView) itemView.findViewById(R.id.tv_more);
        }
    }
    class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title,tv_author;
        private ImageView iv_image;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image1);
        }
    }
    class BottomViweHolder extends RecyclerView.ViewHolder{

        private FrameLayout bottom;

        public BottomViweHolder(View itemView) {
            super(itemView);
            bottom= (FrameLayout) itemView.findViewById(R.id.tv_bottom);
           /* if (isShowFootView){
                bottom.setVisibility(View.VISIBLE);
            }else{
                bottom.setVisibility(View.GONE);
            }*/
        }
    }
}
