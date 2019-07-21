package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;
import com.example.fitmvp.utils.PictureUtil;

import java.io.File;

public class PhotoShow extends AppCompatActivity {
    TextView titleView;
    ImageView foodpic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_show);
        // 返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleView = findViewById(R.id.show1_name);
        foodpic=findViewById(R.id.foodpic);
        Intent intent = getIntent();
        // 获取参数
        String show_name = intent.getStringExtra("foodname");
        //图片还没收
        byte[] show_pic=intent.getByteArrayExtra("picb");
        Bitmap bitmap= PictureUtil.Bytes2Bitmap(show_pic);
        foodpic.setImageBitmap(bitmap);
        // 设置参数
        titleView.setText(show_name);
        ImageButton share=(ImageButton)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFriends();
            }
        });
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

    public void shareFriends(){
        
    }
}
