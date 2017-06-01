package com.gangzi.myprogect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gangzi.myprogect.R;

/**
 * Created by Administrator on 2017/5/31.
 */

public class TypeLeftListAdapter extends BaseAdapter {

    private Context context;
    private  String[] chinese;
    private String[] pinyin;
    private LayoutInflater mInflater;

    private int selectedPosition =0;// 选中的位置

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public TypeLeftListAdapter(Context context, String[] chinese, String[] pinyin) {
        this.context=context;
        this.chinese=chinese;
        this.pinyin=pinyin;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chinese.length;
    }

    @Override
    public Object getItem(int i) {
        return chinese[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view=mInflater.inflate(R.layout.item_left_list,group,false);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_text= (TextView) view.findViewById(R.id.tv_text);
        holder.tv_text.setText(chinese[i]);
        if (selectedPosition == i) {
            view.setBackgroundResource(R.drawable.type_item_background_selector);  //选中项背景
            holder.tv_text.setTextColor(Color.parseColor("#fd3f3f"));
        } else {
            view.setBackgroundResource(R.drawable.bg2);  //其他项背景
            holder.tv_text.setTextColor(Color.parseColor("#323437"));
        }
        return view;
    }

    public void changeSelected(int position){
        if (position!=selectedPosition){
            selectedPosition=position;
            notifyDataSetChanged();
        }
    }

    class ViewHolder{
        private TextView tv_text;
    }
}
