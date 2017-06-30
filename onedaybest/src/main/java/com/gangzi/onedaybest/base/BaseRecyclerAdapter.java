package com.gangzi.onedaybest.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 2017/6/30.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPEHEAD=0;
    public static final int TYPENORMAL=1;
    public static final int TYPEFOOTER=2;

    private ArrayList<T> mDataList=new ArrayList<>();

    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
    }

    public View getHeadView() {
        return headView;
    }

    private View headView;

    public void setHeadViews(List<View> headViews) {
        this.headViews = headViews;
        notifyItemInserted(0);
    }

    public List<View> getHeadViews() {
        return headViews;
    }

    private List<View>headViews;

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    private View footerView;

    public void setFootViews(List<View> footViews) {
        this.footViews = footViews;
        int position;
        if (headViews==null&&headViews.size()<0){
             position=mDataList.size();
             notifyItemInserted(position);
        }else{
             position=mDataList.size()+headViews.size();
             notifyItemInserted(position);
        }
    }

    public List<View> getFootViews() {
        return footViews;
    }

    private List<View>footViews;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public void addData(ArrayList<T>datas){
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPEHEAD;
        }
        if ((headViews==null&&headViews.size()<0)&&(footViews==null&&footViews.size()<0)){
            return TYPENORMAL;
        }
        if (position==headViews.size()+mDataList.size()){
            return TYPEFOOTER;
        }
        return TYPENORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headViews!=null&&headViews.size()>0&&viewType==TYPEHEAD){
            for (int i=0;i<headViews.size();i++){
                return new ViewHolder(headViews.get(i));
            }
        }
        if (footViews!=null&&footViews.size()>0&&viewType==TYPEFOOTER){
            for (int i=0;i<footViews.size();i++){
                return new ViewHolder(headViews.get(i));
            }
        }
       return onCreate(parent,viewType) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPEHEAD){
                return;
        }
        if (getItemViewType(position)==TYPEFOOTER){
            return;
        }
        final int realPosition=getRealPosition(holder);
        final T data = mDataList.get(realPosition);
        onBind(holder, realPosition, data);
        if(mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(realPosition, data);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (headViews!=null&&headViews.size()>0){
            return headViews.size()+mDataList.size();
        }
        if (footViews!=null&&footViews.size()>0){
            return footViews.size()+mDataList.size();
        }
        if ((headViews!=null&&headViews.size()>0)&&(footViews!=null&&footViews.size()>0)){
            return headViews.size()+footViews.size()+mDataList.size();
        }
        return mDataList.size();
    }
   // public abstract

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClick(int positon,T data);
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (headViews!=null&&headViews.size()>0){
            return position=position-1;
        }
        if (footViews!=null&&footViews.size()>0){
            return position=position-1;
        }
        if ((headViews!=null&&headViews.size()>0)&&(footViews!=null&&footViews.size()>0)){
            return position=position-2;
        }
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager= (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position)==TYPEHEAD){
                        return gridLayoutManager.getSpanCount();
                    }
                    if (getItemViewType(position)==TYPEFOOTER){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }
}
