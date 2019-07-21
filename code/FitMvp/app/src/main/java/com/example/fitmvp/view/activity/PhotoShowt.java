package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;
import com.example.fitmvp.utils.PictureUtil;

public class PhotoShowt extends AppCompatActivity{
    TextView food1;
    TextView food2;
    TextView food3;
    TextView food4;
    TextView food5;
    ImageView picc;
    ImageView pics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_showm);
        // 返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        food1 = findViewById(R.id.show2_name1);
        food2 = findViewById(R.id.show2_name2);
        food3 = findViewById(R.id.show2_name3);
        food4 = findViewById(R.id.show2_name4);
        food5 = findViewById(R.id.show2_name5);
        picc=findViewById(R.id.foodpicmc);
        pics=findViewById(R.id.foodpicms);
        Intent intent = getIntent();
        Integer sizeList=intent.getIntExtra("size",1);
        food1.setText(intent.getStringExtra("food1"));
        food2.setText(intent.getStringExtra("food2"));
        food3.setText(intent.getStringExtra("food3"));
        food4.setText(intent.getStringExtra("food4"));
        food5.setText(intent.getStringExtra("food5"));
        byte[] show_pic=intent.getByteArrayExtra("pic");
        Bitmap bitmap= PictureUtil.Bytes2Bitmap(show_pic);
        Integer typePhoto=intent.getIntExtra("type",0);
        if(typePhoto==1){
            picc.setImageBitmap(bitmap);
        }
        else{
            pics.setImageBitmap(bitmap);
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
