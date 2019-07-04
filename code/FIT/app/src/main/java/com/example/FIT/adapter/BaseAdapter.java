package com.example.FIT.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.MyHolder>{
    private List<T> dataList;
    public BaseAdapter(List<T> datas){
        this.dataList = datas;
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.get(parent,getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        convert(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract void convert(MyHolder holder, T data, int position);

    public static class MyHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        private MyHolder(View v){
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static MyHolder get(ViewGroup parent, int layoutId){
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new MyHolder(convertView);
        }

        public <T extends View> T getView(int id){
            View v = mViews.get(id);
            if(v == null){
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T)v;
        }

        // 设置部件高度
        public void setHight(int height){
            ViewGroup.LayoutParams layoutParams = mConvertView.getLayoutParams();
            layoutParams.height = height;
            mConvertView.setLayoutParams(layoutParams);

        }
        public void setText(int id, String value){
            TextView view = getView(id);
            view.setText(value);
        }

        // 设置的图片在项目中
        public void setImage(int id, Integer value){
            ImageView view = getView(id);
            view.setImageResource(value);
        }
    }
}