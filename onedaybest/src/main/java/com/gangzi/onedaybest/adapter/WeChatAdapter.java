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
 * Created by dan on 2017/7/24.
 */

public class WeChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<WeChatData.ResultBean.ListBean> dataList;

    private CenterListAdapter centerAdapter;

    public static final int MORE=0;
    public static final int CENTERLIST=1;
    public static final int BOTTOM=2;

    private int currentType=MORE;

    public WeChatAdapter(Context context, List<WeChatData.ResultBean.ListBean> data) {
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
            //dataList.addAll(position,data);
            //notifyItemRangeChanged(position,dataList.size());
            centerAdapter.addData(position,data);
        }
    }

    public int getcountData() {
        return centerAdapter.getItemCount();
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
