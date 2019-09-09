package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.fitmvp.R;
import com.example.fitmvp.utils.PictureUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class FoodShare extends AppCompatActivity {
    Button save;
    Button share;
    String name;
    Double cal;
    Double protein;
    Double fat;
    Double ch2o;
    Bitmap foodImage;
    ImageView spic;
    TextView scal;
    TextView spro;
    TextView sfat;
    TextView sname;
    TextView sch2o;
    Uri shareUri;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_share);
        Intent intent = getIntent();
        // 获取参数
        name = intent.getStringExtra("name");
        cal=intent.getDoubleExtra("cal1",0);

        protein=intent.getDoubleExtra("protein1",0);
        fat=intent.getDoubleExtra("fat1",0);
        ch2o=intent.getDoubleExtra("carbo1",0);
        Integer type_share=intent.getIntExtra("typeShare",1);
        byte[] fpic = intent.getByteArrayExtra("pic1");
        save = findViewById(R.id.savephoto1);
        share=findViewById(R.id.sharephoto1);
        foodImage = PictureUtil.Bytes2Bitmap(fpic);
        if(type_share==1){
            spic=findViewById(R.id.share_pic);
            spic.setImageBitmap(foodImage);
        }
        else if(type_share==2){
            spic=findViewById(R.id.share_pic2);
            spic.setImageBitmap(foodImage);
        }
        else {
            spic=findViewById(R.id.share_pic3);
            spic.setImageBitmap(foodImage);
        }

        sname=findViewById(R.id.share_name);
        sname.setText(name);
        scal=findViewById(R.id.share_cal);String s0=String.format(Locale.getDefault(), "%.2f", cal);
        scal.setText(s0);
        spro=findViewById(R.id.share_pro);String s1=String.format(Locale.getDefault(), "%.2f", protein);
        spro.setText(s1);
        sfat=findViewById(R.id.share_fat);String s2=String.format(Locale.getDefault(), "%.2f", fat);
        sfat.setText(s2);
        sch2o=findViewById(R.id.share_ch2o);String s3=String.format(Locale.getDefault(), "%.2f", ch2o);
        sch2o.setText(s3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存图片
                View viewsave=findViewById(R.id.share_view1);
                viewsave.setDrawingCacheEnabled(true);
                viewsave.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                viewsave.setDrawingCacheBackgroundColor(Color.WHITE);
                // 把一个View转换成图片
                Bitmap saveView = loadBitmapFromView(viewsave);
               shareUri=stest(saveView,"hi");
               System.out.println("ok");
                save.setVisibility(View.INVISIBLE);
                share.setVisibility(View.VISIBLE);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFriends();
            }
        });

}

    public void shareFriends() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("image/*");  //设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_STREAM, shareUri);
        try {
            share_intent = Intent.createChooser(share_intent, "dialogTitle");
            startActivity(share_intent);
        } catch (Exception e) {
            Toast.makeText(FoodShare.this, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    //储存图片并返回本图片的uri
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
            FoodShare.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
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


}