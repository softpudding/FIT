package com.example.fitmvp.view.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;
import com.example.fitmvp.adapter.FoodAdapter;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.bean.FoodItem;
import com.example.fitmvp.contract.FriendSearchContract;
import com.example.fitmvp.utils.PictureUtil;

import java.util.LinkedList;
import java.util.List;

import static com.activeandroid.Cache.getContext;

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
        //点击
        list_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) ((TextView) view.findViewById(R.id.foodimuch)).getText();
                // String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;
                //点击第0项，文本内容为：101，ID为：0
                Integer itemMuch = Integer.valueOf(text);//本项的重量
                changeNum(itemMuch);
            }
            });
 }



//修改某项的重量
 public void changeNum(Integer integer){
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