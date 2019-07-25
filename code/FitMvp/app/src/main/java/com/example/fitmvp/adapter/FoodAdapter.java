package com.example.fitmvp.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitmvp.R;
import com.example.fitmvp.bean.FoodItem;

import java.util.LinkedList;

public class FoodAdapter extends BaseAdapter{
    private LinkedList<FoodItem> fdata;
    private Context fContext;
    private boolean isClick = false;

    public FoodAdapter(LinkedList<FoodItem> fdata,Context fContext){
        this.fdata=fdata;
        this.fContext=fContext;
    }
    @Override
    public int getCount(){
        return fdata.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(fContext).inflate(R.layout.food_item, parent, false);
        ImageView foodpic = (ImageView)convertView.findViewById(R.id.foodipic);
        TextView foodname = (TextView) convertView.findViewById(R.id.foodiname);
        TextView foodmuch = (TextView) convertView.findViewById(R.id.foodimuch);
        TextView fooden= (TextView) convertView.findViewById(R.id.foodienergy);
        foodpic.setImageBitmap(fdata.get(position).getBitmap());
        foodname.setText(fdata.get(position).getFoodname());
        String s1=fdata.get(position).getWeight().toString();
        foodmuch.setText(s1);
        String s2=fdata.get(position).getEnergy().toString();
        fooden.setText(s2);
        return convertView;
    }
    public void setClick(boolean click) {
        this.isClick = click;
    }
}
