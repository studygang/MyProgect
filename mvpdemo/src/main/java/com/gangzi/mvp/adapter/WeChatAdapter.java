package com.gangzi.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangzi.mvp.R;
import com.gangzi.mvp.bean.WeChatData;

import java.util.List;

/**
 * Created by dan on 2017/7/24.
 */

public class WeChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<WeChatData.ResultBean.ListBean> dataList;

  //  private CenterListAdapter centerAdapter;

    public boolean isLoading;

    public static final int MORE=0;
    public static final int CENTERLIST=1;
    public static final int BOTTOM=2;

    private int currentType=MORE;

    public WeChatAdapter(Context context, List<WeChatData.ResultBean.ListBean> data) {
        this.mContext=context;
        this.dataList=data;
        mInflater=LayoutInflater.from(mContext);
       // centerAdapter=new CenterListAdapter(mContext,dataList);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return MORE;
        }else {
            return CENTERLIST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==MORE){
            View view1=mInflater.inflate(R.layout.more_item,parent,false);
            return new MoreViewHolder(view1,mContext);
        }else if (viewType==CENTERLIST){
            View view2=mInflater.inflate(R.layout.item_center_list,parent,false);
            return new CenterViewHolder(view2,mContext);
        }/*else if (viewType==BOTTOM){
            View view3=mInflater.inflate(R.home_fragment.item_bottom,parent,false);
            return new BottomViewHolder(view3,mContext);
        }*/
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==MORE){
            MoreViewHolder moreViewHolder= (MoreViewHolder) holder;
        }else if (getItemViewType(position)==CENTERLIST){
            CenterViewHolder centerViewHolder= (CenterViewHolder) holder;
            centerViewHolder.setData(position);
        }/*else if (getItemViewType(position)==BOTTOM){
            BottomViewHolder bottomViewHolder= (BottomViewHolder) holder;
        }*/
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    public void clear() {
        if (dataList!=null&&dataList.size()>0){
            dataList.clear();
            notifyDataSetChanged();
            //notifyItemRangeChanged(0,dataList.size()+1);
            //centerAdapter.cleanData();
        }
    }

    public void addData(int position, List<WeChatData.ResultBean.ListBean> data) {

        if (data!=null&&data.size()>0){
            dataList.addAll(position,data);
            notifyItemRangeChanged(position,dataList.size());
        }
    }

    public int getcountData() {
       // return centerAdapter.getItemCount();
        return dataList.size();
    }


    class MoreViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;

        public MoreViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext=context;
        }
    }


    class CenterViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_title,tv_author;
        private ImageView iv_image;

        public CenterViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext=context;
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image1);
        }

        public void setData(int position) {
            String from=dataList.get(position-1).getSource();
            String title=dataList.get(position-1).getTitle();
            String url=dataList.get(position-1).getUrl();
            String firstImageUrl=dataList.get(position-1).getFirstImg();
            tv_title.setText(title);
            tv_author.setText(from);
            Glide.with(mContext).load(firstImageUrl).into(iv_image);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;

        public BottomViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext=context;
        }
    }
}
