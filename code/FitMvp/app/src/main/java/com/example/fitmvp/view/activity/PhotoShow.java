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
        ImageButton share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 shareFriends(bitmap, show_name);
//                shareFriend();
            }
        });
    }

    private Uri saveImage(Bitmap bitmap) {

        File path = getCacheDir();

        String fileName = "shareImage.png";

        File file = new File(path, fileName);

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri mUriPath = Uri.parse("file://" + file.getAbsolutePath());
        return mUriPath;
    }

    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(externalPubPath, imageName);
        Uri uri = null;
        if(picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }

        return uri;
    }

    public void shareFriends(Bitmap imgBit, String name) {
//        ShareView shareView = new ShareView(PhotoShow.this);
//        shareView.setInfo(show_name);
//        final Bitmap image = shareView.createImage();
//        final String path = saveImage(image);
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        //share_intent.setType("image/*");  //设置分享内容的类型
        share_intent.setType("text/plain");
        Uri uri = saveImage(imgBit);
        System.out.println("这是uri：");
      //  share_intent.putExtra(Intent.EXTRA_STREAM, uri);
        //创建分享的Dialog
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
