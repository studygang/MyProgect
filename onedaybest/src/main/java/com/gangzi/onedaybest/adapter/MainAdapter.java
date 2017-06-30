package com.gangzi.onedaybest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gangzi.onedaybest.R;
import com.gangzi.onedaybest.bean.WeChatData;

import java.util.List;

/**
 * Created by gangzi on 2017/6/23.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    private CenterListAdapter centerAdapter;

    private List<WeChatData.ResultBean.ListBean> dataList;

    public static final int MORE=0;
    public static final int CENTERLIST=1;
    public static final int BOTTOM=2;

    private int currentType=MORE;

    public MainAdapter(Context context, List<WeChatData.ResultBean.ListBean> data) {
        this.mContext=context;
        this.dataList=data;
        mInflater=LayoutInflater.from(mContext);
        centerAdapter=new CenterListAdapter(mContext,dataList);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case MORE:
                currentType=MORE;
                break;
            case CENTERLIST:
                currentType=CENTERLIST;
                break;
            case BOTTOM:
                currentType=BOTTOM;
                break;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==MORE){
            View view1=mInflater.inflate(R.layout.more_item,parent,false);
            return new MoreViewHolder(view1,mContext);
        }else if (viewType==CENTERLIST){
            View view2=mInflater.inflate(R.layout.item_center,parent,false);
            return new CenterViewHolder(view2,mContext);
        }else if (viewType==BOTTOM){
            View view3=mInflater.inflate(R.layout.item_bottom,parent,false);
            return new BottomViewHolder(view3,mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==MORE){
            MoreViewHolder moreViewHolder= (MoreViewHolder) holder;
        }else if (getItemViewType(position)==CENTERLIST){
            CenterViewHolder centerViewHolder= (CenterViewHolder) holder;
            centerViewHolder.setData();
        }else if (getItemViewType(position)==BOTTOM){
            BottomViewHolder bottomViewHolder= (BottomViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void cleanData() {
        //CenterListAdapter centerAdapter=new CenterListAdapter(mContext,dataList);
        if (centerAdapter!=null){
            centerAdapter.cleanData();
        }
       // centerAdapter=new CenterListAdapter(mContext,dataList);
       /* dataList.clear();
        dataList.addAll(data);
        notifyItemRangeChanged(0,dataList.size());*/
    }

    public void addData(List<WeChatData.ResultBean.ListBean> data) {
        if (centerAdapter!=null){
            centerAdapter.addData(data);
        }
    }

    public int getcountData() {
        return centerAdapter.getcountData();
    }

    public void addData(int i, List<WeChatData.ResultBean.ListBean> data) {
        if (centerAdapter!=null){
            centerAdapter.addData(i,data);
        }
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
        private RecyclerView recycle;

        public CenterViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext=context;
            recycle= (RecyclerView) itemView.findViewById(R.id.center_recycle);
        }

        public void setData() {
            LinearLayoutManager mamager=new LinearLayoutManager(mContext);
            recycle.setLayoutManager(mamager);
            centerAdapter=new CenterListAdapter(mContext,dataList);
            recycle.setAdapter(centerAdapter);
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
