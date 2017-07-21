package com.gangzi.onedaybest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gangzi on 2017/6/23.
 */

public class CenterListAdapter4 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<WeChatData.ResultBean.ListBean> dataList;
    private final LayoutInflater mLayoutInflater;

    private static final int TYPE_BANNER=0;
    private static final int TYPE_MORE=1;
    private static final int TYPE_LIST=2;
    private static final int TYPE_BOTTOM=3;

    private boolean isLoadMore;


    private boolean isShowFootView;

    public CenterListAdapter4(Context mContext, List<WeChatData.ResultBean.ListBean> dataList) {
        this.mContext=mContext;
        this.dataList=dataList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_BANNER;
        }else if (position==1){
            return TYPE_MORE;
        }else if (position==dataList.size()+2){
            return TYPE_BOTTOM;
        }else{
            return TYPE_LIST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_BANNER){
            return  new BannerViewHolder(mLayoutInflater.inflate(R.layout.item_banner,parent,false));
        }else if (viewType==TYPE_MORE){
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
        if (holder instanceof BannerViewHolder){
            setBanner((BannerViewHolder)holder,position);
        } else if (holder instanceof NewsViewHolder){
            setNews((NewsViewHolder)holder,position);
        }else if (holder instanceof BottomViweHolder){
            changeStatus((BottomViweHolder)holder,position);
        }

    }

    private void changeStatus(BottomViweHolder holder, int position) {
       // if (isLoadMore)
        if (isShowFootView){
            holder.bottom.setVisibility(View.VISIBLE);
            holder.bottom_loading.setVisibility(View.GONE);
        }else{
            holder.bottom.setVisibility(View.GONE);
            holder.bottom_loading.setVisibility(View.VISIBLE);
        }
    }

    private void setBanner(BannerViewHolder holder, int position) {

        List<String> images=new ArrayList<>();
        images.add("http://zxpic.gtimg.com/infonew/0/wechat_pics_-214521.jpg/168");
        images.add("http://zxpic.gtimg.com/infonew/0/wechat_pics_-214279.jpg/168");
        images.add("http://zxpic.gtimg.com/infonew/0/wechat_pics_-214267.jpg/168");

        holder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        holder.banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        holder.banner.setImages(images);
        //设置banner动画效果
       // holder.banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
       // holder.banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        holder.banner.isAutoPlay(true);
        //设置轮播时间
        holder.banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        holder.banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        holder.banner.start();
    }

    private void setNews(NewsViewHolder holder, final int position) {
        WeChatData.ResultBean.ListBean data=dataList.get(position-2);
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
        return dataList.size()+3;
        //return 1;
    }

    public void cleanData() {
        dataList.clear();
        notifyItemRangeChanged(0,dataList.size());
    }

    public void addData(int position, List<WeChatData.ResultBean.ListBean> datas) {
        if (datas!=null&&datas.size()>0){
            dataList.addAll(position,datas);
            notifyItemRangeChanged(position,dataList.size());
          //  notifyItemInserted(position);
        }
    }

    public int getcountData() {
        return dataList.size();
    }

    public void setLoadMoreData(List<WeChatData.ResultBean.ListBean> data) {
        int size=dataList.size();
        dataList.addAll(data);
        notifyItemInserted(size);
    }

    public void setNewData(List<WeChatData.ResultBean.ListBean> data) {
        //dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void setBottomView(boolean b) {
        if (b){
            isShowFootView=false;
        }else{
            isShowFootView=true;
        }
    }

    public void setLoading(boolean b) {
        if (b){
            isLoadMore=true;
        }else{
            isLoadMore=false;
        }
    }


    class MoreViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_more;

        public MoreViewHolder(View itemView) {
            super(itemView);
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
        private LinearLayout bottom_loading;

        public BottomViweHolder(View itemView) {
            super(itemView);
            bottom= (FrameLayout) itemView.findViewById(R.id.tv_bottom);
            bottom_loading= (LinearLayout) itemView.findViewById(R.id.bottom_loading);
        }
    }
    class BannerViewHolder extends RecyclerView.ViewHolder{

        Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner= (Banner) itemView.findViewById(R.id.banner);
        }
    }
}
