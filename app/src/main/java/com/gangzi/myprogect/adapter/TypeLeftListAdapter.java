package com.gangzi.myprogect.adapter;

import android.content.Context;
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

    private int selectedPosition = -1;// 选中的位置

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
      /*  if (selectedPosition==i){
           // setSelected(true);
            view.setBackgroundResource(R.color.red);
        }else{
           // setSelected(false);
            view.setBackgroundResource(R.color.list_background);
        }*/
        return view;
    }
    class ViewHolder{
        private TextView tv_text;
    }
}
