package com.example.fitmvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import com.example.fitmvp.R;
import com.example.fitmvp.utils.PictureUtil;
import com.example.fitmvp.view.fragment.ShareView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoShow extends AppCompatActivity {
    TextView titleView;
    ImageView foodpic;
    Bitmap bitmap;
    String show_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_show);
        // 返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleView = findViewById(R.id.show1_name);
        foodpic = findViewById(R.id.foodpic);
        Intent intent = getIntent();
        // 获取参数
        show_name = intent.getStringExtra("foodname");
        //图片还没收
        byte[] show_pic = intent.getByteArrayExtra("picb");
        bitmap = PictureUtil.Bytes2Bitmap(show_pic);
        foodpic.setImageBitmap(bitmap);
        // 设置参数
        titleView.setText(show_name);
        final Uri urithis = stest(bitmap,show_name);
        ShareView shareView = new ShareView(PhotoShow.this);
        shareView.setInfo(show_name);
        shareView.setUriview(urithis);
        final Bitmap image = shareView.createImage(bitmap,show_name);
        final Uri urithat=stest(image,show_name);
        ImageButton share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFriends(urithat, show_name);
//                shareFriend();
            }
        });
    }

    public Uri stest(Bitmap b, String name){
        //生成路径
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dirName = "singleFoodShare";
        File appDir = new File(root , dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //文件名为时间
        long timeStamp = System.currentTimeMillis();
        String sd1=String.valueOf(timeStamp);
        String fileName = sd1 + ".jpg";

        //获取文件
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            PhotoShow.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this,"com.example.fitmvp.fileProvider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            return uri;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void shareFriends(Uri uri, String name) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("image/*");  //设置分享内容的类型
       share_intent.putExtra(Intent.EXTRA_STREAM, uri);
        //创建分享的Dialog

       // share_intent.putExtra(Intent.EXTRA_TEXT,name);
        // share_intent = Intent.createChooser(share_intent,"分享");
        // startActivity(share_intent);
        try {
            share_intent = Intent.createChooser(share_intent, "dialogTitle");
            startActivity(share_intent);
        } catch (Exception e) {
            Toast.makeText(PhotoShow.this, "分享失败", Toast.LENGTH_SHORT).show();
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
