package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    String f1;
    String f2;
    String f3;
    String f4;
    String f5;
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
        f1=intent.getStringExtra("food1");
        food1.setText(f1);
        f2=intent.getStringExtra("food2");
        food2.setText(f2);
        f3=intent.getStringExtra("food3");
        food3.setText(f3);
        f4=intent.getStringExtra("food4");
        food4.setText(f4);
        f5=intent.getStringExtra("food5");
        food5.setText(f5);
        byte[] show_pic=intent.getByteArrayExtra("pic");
        Bitmap bitmap= PictureUtil.Bytes2Bitmap(show_pic);
        Integer typePhoto=intent.getIntExtra("type",0);
        if(typePhoto==1){
            picc.setImageBitmap(bitmap);
        }
        else{
            pics.setImageBitmap(bitmap);
        }
        ImageButton share = (ImageButton) findViewById(R.id.sharem);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFriends(f1,f2,f3,f4,f5);
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

    public void shareFriends(String name1,String name2,String name3,String name4,String name5) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");
        String name=name1+" "+name2+" "+name3+" "+name4+" "+name5;
        share_intent.putExtra(Intent.EXTRA_TEXT,name);
        // share_intent = Intent.createChooser(share_intent,"分享");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");//添加分享内容标题
        // startActivity(share_intent);
        try {
            share_intent = Intent.createChooser(share_intent, "dialogTitle");
            startActivity(share_intent);
        } catch (Exception e) {
            // error
            // sometime , there is no app to share
            Toast.makeText(PhotoShowt.this, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }
}
