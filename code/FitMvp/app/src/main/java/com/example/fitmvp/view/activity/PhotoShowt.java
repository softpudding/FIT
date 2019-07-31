package com.example.fitmvp.view.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
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
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.adapter.FoodAdapter;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.bean.FoodItem;
import com.example.fitmvp.contract.FriendSearchContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.PictureUtil;

import java.util.LinkedList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static com.activeandroid.Cache.getContext;

public class PhotoShowt extends AppCompatActivity{
    ImageView picc;
    ImageView pics;
    Integer sizeList;
    String f1;String f2;String f3;String f4;
    Integer cal1; Integer cal2;Integer cal3;Integer cal4;
    Double fat1;Double fat2;Double fat3;Double fat4;
    Double prote1;Double prote2;Double prote3;Double prote4;
    Double carbo1;Double carbo2;Double carbo3;Double carbo4;
    Integer weight1;Integer weight2;Integer weight3;Integer weight4;
    Integer x1;Integer x2;Integer x3;Integer x4;
    Integer y1;Integer y2;Integer y3;Integer y4;
    Integer w1;Integer w2;Integer w3;Integer w4;
    Integer h1;Integer h2;Integer h3;Integer h4;
    Bitmap bitmap;
    Button sharem_sure;
    Button show2_share;
    ImageView sharem_wait;
    private ListView list_food;
    private List<FoodItem> fdata=null;
    private FoodAdapter fAdapter=null;
    private Context fContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_showm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 返回键
        picc=findViewById(R.id.foodpicmc);
        pics=findViewById(R.id.foodpicms);
        //获取食物参数
        Intent intent = getIntent();
        sizeList=intent.getIntExtra("size",1);
        f1=intent.getStringExtra("food1");f2=intent.getStringExtra("food2");
        f3=intent.getStringExtra("food3");f4=intent.getStringExtra("food4");
        cal1=intent.getIntExtra("calory1",0);cal2=intent.getIntExtra("calory2",0);
        cal3=intent.getIntExtra("calory3",0);cal4=intent.getIntExtra("calory4",0);
        fat1=intent.getDoubleExtra("fat1",0);fat2=intent.getDoubleExtra("fat2",0);
        fat3=intent.getDoubleExtra("fat3",0);fat4=intent.getDoubleExtra("fat4",0);
        prote1=intent.getDoubleExtra("protein1",0);prote2=intent.getDoubleExtra("protein2",0);
        prote3=intent.getDoubleExtra("protein3",0);prote4=intent.getDoubleExtra("protein4",0);
        carbo1=intent.getDoubleExtra("carbohydrate1",0);
        carbo2=intent.getDoubleExtra("carbohydrate2",0);
        carbo3=intent.getDoubleExtra("carbohydrate3",0);
        carbo4=intent.getDoubleExtra("carbohydrate4",0);
        x1=intent.getIntExtra("x1",1);x2=intent.getIntExtra("x2",1);
        x3=intent.getIntExtra("x3",1);x4=intent.getIntExtra("x4",1);
        y1=intent.getIntExtra("y1",1);y2=intent.getIntExtra("y2",1);
        y3=intent.getIntExtra("y3",1);y4=intent.getIntExtra("y4",1);
        w1=intent.getIntExtra("w1",1);w2=intent.getIntExtra("w2",1);
        w3=intent.getIntExtra("w3",1);w4=intent.getIntExtra("w4",1);
        h1=intent.getIntExtra("h1",1);h2=intent.getIntExtra("h2",1);
        h3=intent.getIntExtra("h3",1);h4=intent.getIntExtra("h4",1);
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
//        //列表显示
        initView(bitmap);
        //按钮
        sharem_sure = (Button) findViewById(R.id.show2_sure);
       sharem_wait=(ImageView)findViewById(R.id.imageView2);
       show2_share=(Button)findViewById(R.id.show2_share);
        sharem_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendm();
                sharem_sure.setVisibility(View.INVISIBLE);
                show2_share.setVisibility(View.VISIBLE);
            }
        });

    }
//这里是onCreate结尾

public void sendm(){
    JSONArray hallo=new JSONArray();
    JSONObject jsonObject1=new JSONObject();
    JSONObject jsonObject2=new JSONObject();
    JSONObject jsonObject3=new JSONObject();
    JSONObject jsonObject4=new JSONObject();
    String tel= BaseApplication.getUserEntry().username;
    jsonObject1.put("tel",tel);jsonObject2.put("tel",tel);jsonObject3.put("tel",tel);jsonObject4.put("tel",tel);
    jsonObject1.put("food",f1);jsonObject2.put("food",f2);jsonObject3.put("food",f3);jsonObject4.put("food",f4);
    jsonObject1.put("weight",100);jsonObject2.put("weight",100);jsonObject3.put("weight",100);jsonObject4.put("weight",100);
    jsonObject1.put("fat",fat1);jsonObject2.put("fat",fat2);jsonObject3.put("fat",fat3);jsonObject4.put("fat",fat4);
    jsonObject1.put("protein",prote1);jsonObject2.put("protein",prote2);jsonObject3.put("protein",prote3);jsonObject4.put("protein",prote4);
    jsonObject1.put("carbohydrate",carbo1);jsonObject2.put("carbohydrate",carbo2);jsonObject3.put("carbohydrate",carbo3);jsonObject4.put("carbohydrate",carbo4);
    jsonObject1.put("cal",cal1);jsonObject2.put("cal",cal2);jsonObject3.put("cal",cal3);jsonObject4.put("cal",cal4);
    hallo.add(jsonObject1);hallo.add(jsonObject2);hallo.add(jsonObject3);hallo.add(jsonObject4);
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
}

    // 发送更新主页和记录页面的广播
    private void updateRecords(){
        Intent friendInfoIntent = new Intent("updateRecords");
        LocalBroadcastManager.getInstance(this).sendBroadcast(friendInfoIntent);
    }

 private void initView(Bitmap bitmap){
        fContext=PhotoShowt.this;
        list_food=findViewById(R.id.foodlist);
        fdata=new LinkedList<FoodItem>();
        initfood(fdata,bitmap);
        fAdapter=new FoodAdapter((LinkedList<FoodItem>)fdata,fContext);
        list_food.setAdapter(fAdapter);
        //点击
//        list_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = (String) ((TextView) view.findViewById(R.id.foodimuch)).getText();
//                // String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;
//                //点击第0项，文本内容为：101，ID为：0
//                Integer itemMuch = Integer.valueOf(text);//本项的重量
//                changeNum(itemMuch);
//            }
//            });
 }



//修改某项的重量
 public void changeNum(Integer integer){
 }

 //数据放进item里
    public void initfood(List<FoodItem> fdata,Bitmap bitmap){
        //缺少图片分隔函数未完成
        Bitmap b1=createBitmap(bitmap,x1,y1,w1,h1);
        Bitmap b2=createBitmap(bitmap,x2,y2,w2,h2);
        Bitmap b3=createBitmap(bitmap,x3,y3,w3,h3);
        Bitmap b4=createBitmap(bitmap,x4,y4,w4,h4);
        FoodItem food1 = new FoodItem();
        food1.setBitmap(b1);
        food1.setWeight(100);
        food1.setFoodname(f1);
        food1.setEnergy(cal1);
        fdata.add(food1);
        FoodItem food2= new FoodItem();
        food2.setBitmap(b2);
        food2.setWeight(100);
        food2.setFoodname(f2);
        food2.setEnergy(cal2);
        fdata.add(food2);
        FoodItem food3 = new FoodItem();
        food3.setBitmap(b3);
        food3.setWeight(100);
        food3.setFoodname(f3);
        food3.setEnergy(cal3);
        fdata.add(food3);
        FoodItem food4 = new FoodItem();
        food4.setBitmap(b4);
        food4.setWeight(100);
        food4.setFoodname(f4);
        food4.setEnergy(cal4);
        fdata.add(food4);
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