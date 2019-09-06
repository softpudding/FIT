package com.example.fitmvp.adapter;
import java.util.List;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.example.fitmvp.R;
import com.example.fitmvp.bean.FoodItem;

import java.util.LinkedList;

public class FoodAdapter extends BaseAdapter{
    private TextView foodname;
    private LinkedList<FoodItem> fdata;
    private Context fContext;
    //private boolean isClick = false;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        FoodItem foodItem=(FoodItem)getItem(position);
        convertView = LayoutInflater.from(fContext).inflate(R.layout.food_item, parent, false);
        ImageView foodpic = (ImageView)convertView.findViewById(R.id.foodipic);
        foodname = (TextView) convertView.findViewById(R.id.foodiname);
        EditText foodmuch = (EditText) convertView.findViewById(R.id.foodimuch);
        int maxLength =4;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new InputFilter.LengthFilter(maxLength);
        foodmuch.setFilters(fArray);
        //AppCompatSeekBar show2_pressure = (AppCompatSeekBar) convertView.findViewById(R.id.show2_pressure);
        foodpic.setImageBitmap(fdata.get(position).getBitmap());
        foodname.setText(fdata.get(position).getFoodname());
//        foodname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String a=showListPopWindow(position);
//                foodname.setText("琪超");
//            }
//        });
        String s1=fdata.get(position).getWeight().toString();
        foodmuch.setText(s1);
        foodmuch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FoodItem foodItem1=fdata.get(position);
                String rap = s.toString();
                if(rap.equals("")){
                    rap="0";
                }
                Integer cxk=Integer.valueOf(rap);
                foodItem1.setWeight(cxk);
                fdata.set(position,foodItem1);
            }
        });
    return convertView;
    }
//    public void setClick(boolean click) {
//        this.isClick = click;
//    }
    public String showListPopWindow(int position){
        String showText = "点击第" + position + "项";//第一排是第0项
        System.out.println(showText);
        return "qichao";
    }

}
