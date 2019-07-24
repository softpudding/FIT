package com.example.fitmvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;
import com.example.fitmvp.adapter.FoodAdapter;
import com.example.fitmvp.bean.FoodItem;
import com.example.fitmvp.utils.PictureUtil;

import java.util.LinkedList;
import java.util.List;

public class PhotoShowt extends AppCompatActivity{
    ImageView picc;
    ImageView pics;
    Integer sizeList;
    String f1;
    String f2;
    String f3;
    String f4;
    Bitmap bitmap;
    private ListView list_food;
    private List<FoodItem> fdata=null;
    private FoodAdapter fAdapter=null;
    private Context fContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_showm);
        // 返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        picc=findViewById(R.id.foodpicmc);
        pics=findViewById(R.id.foodpicms);
        Intent intent = getIntent();
        sizeList=intent.getIntExtra("size",1);
        f1=intent.getStringExtra("food1");
        f2=intent.getStringExtra("food2");
        f3=intent.getStringExtra("food3");
        f4=intent.getStringExtra("food4");
        //图片显示
        byte[] show_pic=intent.getByteArrayExtra("pic");
        bitmap= PictureUtil.Bytes2Bitmap(show_pic);
        Integer typePhoto=intent.getIntExtra("type",0);
        if(typePhoto==1){
            picc.setImageBitmap(bitmap);
        }
        else{
            pics.setImageBitmap(bitmap);
        }

        //分享按钮
        ImageButton share = (ImageButton) findViewById(R.id.sharem);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //列表显示
        initView(bitmap);


    }
 private void initView(Bitmap bitmap){
        fContext=PhotoShowt.this;
        list_food=findViewById(R.id.foodlist);
        fdata=new LinkedList<FoodItem>();
        initfood(fdata,bitmap);
        fAdapter=new FoodAdapter((LinkedList<FoodItem>)fdata,fContext);
        list_food.setAdapter(fAdapter);
        //TODO:onclick之后的操作
//        list_food.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
//
//            }
//        }
 }
    public void initfood(List<FoodItem> fdata,Bitmap bitmap){
        //缺少图片分隔函数未完成
        for(int i=1;i<=4;i++){
            FoodItem food1 = new FoodItem();
            food1.setBitmap(bitmap);
            String is= String.valueOf(i);
            food1.setFoodname("food"+ is);
            food1.setWeight(100+i);
            food1.setEnergy(200+i);
            fdata.add(food1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
