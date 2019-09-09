package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
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

public class PhotoShow extends AppCompatActivity{
    Uri urithis_s;
    TextView show1name;
    ImageView foodpic;
    Button enesend;
    Bitmap bitmap;
    Button share1;
    Button save1;
    String show_name;
    Integer energy;
    Double protein;
    Double fat;
    Double carbo;
    ImageView wait_show;
    AppCompatSeekBar sb_pressure;
    TextView et_pressure;
    //TextView kj_pressure;
    String weight;
    Double kalu;
    Double protein1;
    Double cal;
    Double fat1;
    Double carbo1;
    private Paint bitmapPaint;
    private Paint textPaint;
    /**配文的颜色*/
    private int textColor = Color.BLACK;
    /**配文的字体大小*/
    private float textSize = 16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 返回键
        show1name = findViewById(R.id.show1_name);
        show1name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopulWindow();
            }
        });
//        show1name.setOnFocusChangeListener(this);
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
        share1=(Button)findViewById(R.id.share1);
        save1=(Button)findViewById(R.id.baocun1);
        wait_show=(ImageView)findViewById(R.id.wait_show);
        bitmap = PictureUtil.Bytes2Bitmap(show_pic);
        foodpic.setImageBitmap(bitmap);
        show1name.setText(show_name);
        // 滑动条
        sb_pressure = (AppCompatSeekBar) findViewById(R.id.sb_pressure);
        et_pressure = (TextView)findViewById(R.id.et_pressure);
        //kj_pressure=(TextView)findViewById(R.id.show1_ener);
        sb_pressure.setProgress(0);
        String haha1="50";
        et_pressure.setText(haha1);
        sb_pressure.setMax(1450);
        String haha2="100";
        et_pressure.setText(haha2);
        Integer kj2=(Integer.valueOf(haha2))*energy/100;String kjs2=kj2.toString();
      //  kj_pressure.setText(kjs2);
        sb_pressure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p=(50+progress)/10;
                int a=10*p;
                int q=a*energy/100;
                String miao="" + String.valueOf(a);
                String ju=""+String.valueOf(q);
                et_pressure.setText(miao);// 50为进度条滑到最小值时代表的数值
               // kj_pressure.setText(ju);
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
                show_name=(String)show1name.getText();
                JSONObject foodname=new JSONObject();
                foodname.put("name1",show_name);
                foodname.put("name2","NULL");
                foodname.put("name3","NULL");
                foodname.put("name4","NULL");
                Http.getHttpService(1).foodget(foodname)
                        .compose(new ThreadTransformer<JSONArray>())
                        .subscribe(new CommonObserver<JSONArray>() {
                            @Override
                            public void onNext(JSONArray foodinfo) {
                                JSONObject fooddetail = foodinfo.getJSONObject(0);
                                Integer Calory=fooddetail.getInteger("calory");
                                Double Fat=fooddetail.getDouble("fat");
                                Double Carbohydrate=fooddetail.getDouble("carbohydrate");
                                Double Protein=fooddetail.getDouble("protein");
                                weight=et_pressure.getText().toString();
                                kalu=Double.valueOf(weight)/100;
                                protein1=kalu*Protein;
                                cal=Calory*kalu;
                                fat1=kalu*Fat;
                                carbo1=kalu*Carbohydrate;
                                sendene(show_name,kalu,fat1,protein1,carbo1,cal);
                            }
                            @Override
                            public void onError(ApiException e){
                                System.err.println("onError: "+ e.getMessage());
                                System.out.println("没传给琪超！");
                            }
                        });

            }
        });
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFriends();
            }
        });


    }
    //这里是oncreate结尾

//    @Override
//    public void onFocusChange(View v,boolean hasFocus){
//        if (hasFocus) {
//            showListPopulWindow(); //调用显示PopuWindow 函数
//        }
//    }
    private void showListPopulWindow() {
        final String[] list = {
                "菠萝咕老肉","炒白菜","炒菠菜","炒冬瓜","炒海带","炒河粉","炒花菜","炒豇豆","炒芹菜", "炒青菜",
                "炒乌冬面", "炒西兰花","炒玉米","蛋包饭","蛋饺","东坡肉","番茄炒蛋","贡丸汤","汉堡","红烧大排",
                "黄焖鸡米饭","馄饨", "酒酿丸子","咖喱猪排饭","烤鸭","烤鱼","凉拌黄瓜","卤鸡腿","卤肉饭","麻辣豆腐",
                "毛血旺","梅菜扣肉","米饭","披萨","青椒腊肉", "三杯鸡饭","上海炒面","烧茄子","生煎","酸菜牛肉面",
                "酸菜鱼","酸汤肥牛","汤包","汤圆","糖醋里脊","糖醋排骨","铁板牛排意面", "土豆丝","西红柿鸡蛋面","鸭血粉丝汤",
                "扬州炒饭","意面","油爆虾","鱼香肉丝","炸鸡块","炸酱面","炸小鸡腿","蒸鸡蛋","煮鸡蛋"
        };
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(show1name);//以哪个控件为基准，在该处以mEditText为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                show1name.setText(list[i]);//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
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

        toSave();
    }
public void toSave(){
    byte[] pic1=PictureUtil.Bitmap2Bytes(bitmap);
    Intent intent = new Intent(PhotoShow.this,FoodShare.class);
    intent.putExtra("typeShare",1);
    intent.putExtra("pic1",pic1);
    intent.putExtra("name",show_name);
    intent.putExtra("cal1",cal);
    intent.putExtra("protein1",protein1);
    intent.putExtra("fat1",fat1);
    intent.putExtra("carbo1",carbo1);
    wait_show.setVisibility(View.INVISIBLE);
    startActivity(intent);
    finish();
}

    // 发送更新主页和记录页面的广播
    private void updateRecords(){
        Intent friendInfoIntent = new Intent("updateRecords");
        LocalBroadcastManager.getInstance(this).sendBroadcast(friendInfoIntent);
    }

//    public void shareFriends(Uri uri, String name) {
public void shareFriends() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("image/*");  //设置分享内容的类型
       share_intent.putExtra(Intent.EXTRA_STREAM, urithis_s);
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
