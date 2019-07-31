package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.PictureUtil;
import com.example.fitmvp.view.fragment.ShareView;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoShow extends AppCompatActivity {
    TextView show1name;
    ImageView foodpic;
    Button enesend;
    Bitmap bitmap;
    Button share1;
    String show_name;
    Integer energy;
    Double protein;
    Double fat;
    Double carbo;
    ImageView wait_show;
    AppCompatSeekBar sb_pressure;
    TextView et_pressure;
    TextView kj_pressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 返回键
        show1name = findViewById(R.id.show1_name);
        foodpic = findViewById(R.id.foodpic);

        Intent intent = getIntent();
        // 获取参数
        show_name = intent.getStringExtra("foodname");
        energy=intent.getIntExtra("energy",1);
        protein=intent.getDoubleExtra("protein",0);
        fat=intent.getDoubleExtra("fat",0);
        carbo=intent.getDoubleExtra("carbo",0);
        byte[] show_pic = intent.getByteArrayExtra("picb");
        //初始化显示
        share1=(Button)findViewById(R.id.share);
        wait_show=(ImageView)findViewById(R.id.wait_show);
        bitmap = PictureUtil.Bytes2Bitmap(show_pic);
        foodpic.setImageBitmap(bitmap);
        show1name.setText(show_name);
        // 滑动条
        sb_pressure = (AppCompatSeekBar) findViewById(R.id.sb_pressure);
        et_pressure = (TextView)findViewById(R.id.et_pressure);
        kj_pressure=(TextView)findViewById(R.id.show1_ener);
        sb_pressure.setProgress(0);
        String haha1="50";
        et_pressure.setText(haha1);
        sb_pressure.setMax(1450);
        String haha2="100";
        et_pressure.setText(haha2);
        Integer kj2=(Integer.valueOf(haha2))*energy/100;String kjs2=kj2.toString();
        kj_pressure.setText(kjs2);
        sb_pressure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p=(50+progress)/10;
                int a=10*p;
                int q=a*energy/100;
                String miao="" + String.valueOf(a);
                String ju=""+String.valueOf(q);
                et_pressure.setText(miao);// 50为进度条滑到最小值时代表的数值
                kj_pressure.setText(ju);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        //确认记录
        enesend=(Button)findViewById(R.id.show1_sure);
        enesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight=et_pressure.getText().toString();
                Double kalu=Double.valueOf(weight)/100;
                Double protein1=kalu*protein;
                Double cal=energy*kalu;
                Double fat1=kalu*fat;
                Double carbo1=kalu*carbo;
                sendene(show_name,kalu,fat1,protein1,carbo1,cal);

            }
        });
//        final Uri urithis = stest(bitmap,show_name);
//        ShareView shareView = new ShareView(PhotoShow.this);
//        shareView.setInfo(show_name);
//        shareView.setUriview(urithis);
//        final Bitmap image = shareView.createImage(bitmap,show_name);
//        final Uri urithat=stest(image,show_name);
//        ImageButton share = (ImageButton) findViewById(R.id.share);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareFriends(urithat, show_name);
//            }
//        });

    }
    //这里是oncreate结尾

    public void sendene(String show_name,Double kalu,Double fat1, Double protein1,Double carbo1,Double cal){
        enesend.setVisibility(View.INVISIBLE);
        wait_show.setVisibility(View.VISIBLE);
        JSONArray hallo=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        String tel= BaseApplication.getUserEntry().username;
        kalu=kalu*100;
        jsonObject.put("tel",tel);
        jsonObject.put("food",show_name);
        jsonObject.put("weight",kalu);
        jsonObject.put("fat",fat1);
        jsonObject.put("protein",protein1);
        jsonObject.put("carbohydrate",carbo1);
        jsonObject.put("cal",cal);
        hallo.add(jsonObject);
        System.out.println(show_name);
        System.out.println(protein1);
        Http.getHttpService(1).saveRecord(hallo)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    public void onNext(String response) {
                        System.out.println(response);//返回了"1"
                        // 更新主页和记录页面
                        updateRecords();
                    }
                    @Override
                    public void onError(ApiException e){
                        System.err.println("onError: "+ e.getMessage());
                        System.out.println("没传过去！");
                    }
                });

        wait_show.setVisibility(View.INVISIBLE);
        share1.setVisibility(View.VISIBLE);
    }

    // 发送更新主页和记录页面的广播
    private void updateRecords(){
        Intent friendInfoIntent = new Intent("updateRecords");
        LocalBroadcastManager.getInstance(this).sendBroadcast(friendInfoIntent);
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
