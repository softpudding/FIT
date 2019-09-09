package com.example.fitmvp.base;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.MyHolder>{
    private List<T> dataList;
    // 事件回调监听
    private BaseAdapter.OnItemClickListener onItemClickListener;

    public BaseAdapter(List<T> datas){
        this.dataList = datas;
    }

    public void setDataList(List<T> list){
        dataList = list;
    }

    public abstract int getLayoutId(int viewType);


    // 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // 定义一个设置点击监听器的方法
    public void setOnItemClickListener(BaseAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.get(parent,getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        convert(holder, dataList.get(position), position);
        // 对RecyclerView的每一个itemView设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
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

        public void setVisible(int id, int type){
            TextView view = getView(id);
            view.setVisibility(type);
        }

        // 设置的图片在项目中
        public void setImage(int id, Integer value){
            ImageView view = getView(id);
            view.setImageResource(value);
        }

        public void setImage(int id, Bitmap bitmap){
            ImageView view = getView(id);
            view.setImageBitmap(bitmap);
        }
    }
}