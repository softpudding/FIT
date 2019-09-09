package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
    Double wei1,wei2,wei3,wei4;
    ImageView picc;
    ImageView pics;
    Integer sizeList;
    Integer flistSize;
    String f1;String f2;
    String f3;String f4;
    Integer cal1=0; Integer cal2=0;
    Integer cal3=0;Integer cal4=0;
    Integer calAll=0;
    Double fat1=0.0;Double fat2=0.0;
    Double fat3=0.0;Double fat4=0.0;
    Double fatAll=0.0;
    Double prote1=0.0;Double prote2=0.0;
    Double prote3=0.0;Double prote4=0.0;
    Double proteAll=0.0;
    Double carbo1=0.0;Double carbo2=0.0;
    Double carbo3=0.0;Double carbo4=0.0;
    Double carboAll=0.0;
    Integer weight1;Integer weight2;
    Integer weight3;Integer weight4;
    Integer x1;Integer x2;Integer x3;Integer x4;
    Integer y1;Integer y2;Integer y3;Integer y4;
    Integer w1;Integer w2;Integer w3;Integer w4;
    Integer h1;Integer h2;Integer h3;Integer h4;
    Bitmap bitmap;
    String nameAll;
    Button sharem_sure;
    Button show2_share;
    Button addi;
    ImageView sharem_wait;
    Integer typePhoto;
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
        addi=findViewById(R.id.add_item);
        //获取食物参数
        Intent intent = getIntent();
        sizeList=intent.getIntExtra("size",1);
        f1=intent.getStringExtra("food1");f2=intent.getStringExtra("food2");
        f3=intent.getStringExtra("food3");f4=intent.getStringExtra("food4");
//        f1="生煎";f2="苑齐超不知道";
//        f3="西红柿鸡蛋面";f4="苑齐超不知道";
        x1=intent.getIntExtra("x1",1);
        x2=intent.getIntExtra("x2",1);
        x3=intent.getIntExtra("x3",1);
        x4=intent.getIntExtra("x4",1);
        y1=intent.getIntExtra("y1",1);
        y2=intent.getIntExtra("y2",1);
        y3=intent.getIntExtra("y3",1);
        y4=intent.getIntExtra("y4",1);
        w1=intent.getIntExtra("w1",1);
        w2=intent.getIntExtra("w2",1);
        w3=intent.getIntExtra("w3",1);
        w4=intent.getIntExtra("w4",1);
        h1=intent.getIntExtra("h1",1);
        h2=intent.getIntExtra("h2",1);
        h3=intent.getIntExtra("h3",1);
        h4=intent.getIntExtra("h4",1);
        //图片显示
        byte[] show_pic=intent.getByteArrayExtra("pic");
        bitmap= PictureUtil.Bytes2Bitmap(show_pic);
        typePhoto=intent.getIntExtra("type",0);
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
            }
        });
        addi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fdata.size()<4){
                    FoodItem food5 = new FoodItem();
                    food5.setBitmap(null);
                    food5.setWeight(100);
                    food5.setFoodname("米饭");
                    fdata.add(food5);
                    list_food.setAdapter(fAdapter);
                }
                else{
                    new AlertDialog.Builder(PhotoShowt.this).setTitle("最多可添加四种")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }

            }
        });
    }
//这里是onCreate结尾
public void testm(){
    System.out.println(fdata.get(0).getFoodname());
    System.out.println(fdata.get(1).getFoodname());
    System.out.println(fdata.size());
    System.out.println(fdata.get(0).getWeight());
    System.out.println(fdata.get(1).getWeight());
  // int age = Integer.parseInt(list_food.getText().toString());

}
public void sendm(){
        //传给琪超，然后获得数据后再返回去
    Integer foodnum=fdata.size();
    f1="NULL";f2="NULL";f3="NULL";f4="NULL";
    if(foodnum>=1){
        f1=fdata.get(0).getFoodname();
        weight1=fdata.get(0).getWeight();
    }
    if(foodnum>=2){
        f2=fdata.get(1).getFoodname();
        weight2=fdata.get(1).getWeight();
    }
    if(foodnum>=3){
        f3=fdata.get(2).getFoodname();
        weight3=fdata.get(2).getWeight();
    }
    if(foodnum==4){
        f4=fdata.get(3).getFoodname();
        weight4=fdata.get(3).getWeight();
    }
JSONObject foodsname=new JSONObject();
    foodsname.put("name1",f1);
    foodsname.put("name2",f2);
    foodsname.put("name3",f3);
    foodsname.put("name4",f4);

    Http.getHttpService(1).foodget(foodsname)
            .compose(new ThreadTransformer<JSONArray>())
            .subscribe(new CommonObserver<JSONArray>() {
                @Override
                public void onNext(JSONArray foodinfo) {
                    JSONObject fooddetail1;JSONObject fooddetail2;JSONObject fooddetail3;JSONObject fooddetail4;
                    flistSize=foodinfo.size();
                    if(flistSize>=1){
                        wei1=(Double.valueOf(weight1.toString()))/100;
                        fooddetail1 = foodinfo.getJSONObject(0);
                        f1=fooddetail1.getString("name");
                        nameAll=f1;
                        fat1=wei1*fooddetail1.getDouble("fat");
                        prote1=wei1*fooddetail1.getDouble("protein");
                        cal1=(weight1*fooddetail1.getInteger("calory"))/100;
                        carbo1=wei1*fooddetail1.getDouble("carbohydrate");
                    }
                    if(flistSize>=2){
                        wei2=(Double.valueOf(weight2.toString()))/100;
                        fooddetail2 = foodinfo.getJSONObject(1);
                        f2=fooddetail2.getString("name");
                        nameAll+=";"+f2;
                        fat2=wei2*fooddetail2.getDouble("fat");
                        prote2=wei2*fooddetail2.getDouble("protein");
                        cal2=(weight2*fooddetail2.getInteger("calory"))/100;
                        carbo2=wei2*fooddetail2.getDouble("carbohydrate");
                    }
                    if(flistSize>=3){
                        wei3=(Double.valueOf(weight3.toString()))/100;
                        fooddetail3 = foodinfo.getJSONObject(2);
                        f3=fooddetail3.getString("name");
                        nameAll+=";"+f3;
                        fat3=wei3*fooddetail3.getDouble("fat");
                        prote3=wei3*fooddetail3.getDouble("protein");
                        cal3=(weight3*fooddetail3.getInteger("calory"))/100;
                        carbo3=wei3*fooddetail3.getDouble("carbohydrate");
                    }
                    if(flistSize>=4){
                        wei4=(Double.valueOf(weight4.toString()))/100;
                        fooddetail4 = foodinfo.getJSONObject(3);
                        f4=fooddetail4.getString("name");
                        nameAll+=";"+f4;
                        fat4=wei4*fooddetail4.getDouble("fat");
                        prote4=wei4*fooddetail4.getDouble("protein");
                        cal4=(weight4*fooddetail4.getInteger("calory"))/100;
                        carbo4=wei4*fooddetail4.getDouble("carbohydrate");
                    }
                   sendrealInfo();
                }
                @Override
                public void onError(ApiException e){
                    System.err.println("onError: "+ e.getMessage());
                    System.out.println("没传给琪超！");
                }
            });
}

public void sendrealInfo(){
    JSONArray hallo=new JSONArray();
    JSONObject jsonObject1=new JSONObject();
    JSONObject jsonObject2=new JSONObject();
    JSONObject jsonObject3=new JSONObject();
    JSONObject jsonObject4=new JSONObject();
    String tel= BaseApplication.getUserEntry().username;
    jsonObject2.put("tel",tel);
    jsonObject3.put("tel",tel);
    jsonObject4.put("tel",tel);
    jsonObject2.put("food",f2);
    jsonObject3.put("food",f3);
    jsonObject4.put("food",f4);
    jsonObject1.put("weight",weight1);
    jsonObject2.put("weight",weight2);
    jsonObject3.put("weight",weight3);
    jsonObject4.put("weight",weight4);
    jsonObject1.put("fat",fat1);
    jsonObject2.put("fat",fat2);
    jsonObject3.put("fat",fat3);
    jsonObject4.put("fat",fat4);
    jsonObject1.put("protein",prote1);
    jsonObject2.put("protein",prote2);
    jsonObject3.put("protein",prote3);
    jsonObject4.put("protein",prote4);
    jsonObject1.put("carbohydrate",carbo1);
    jsonObject2.put("carbohydrate",carbo2);
    jsonObject3.put("carbohydrate",carbo3);
    jsonObject4.put("carbohydrate",carbo4);
    jsonObject1.put("cal",cal1);
    jsonObject2.put("cal",cal2);
    jsonObject3.put("cal",cal3);
    jsonObject4.put("cal",cal4);
    if(flistSize>=1) {
        jsonObject1.put("tel",tel);
        jsonObject1.put("food",f1);
        hallo.add(jsonObject1);
    }
    if(flistSize>=2) {
        hallo.add(jsonObject2);
    }
    if(flistSize>=3) {
        hallo.add(jsonObject3);
    }
    if(flistSize>=4) {
        hallo.add(jsonObject4);
    }
    System.out.println("okk");
    Http.getHttpService(1).saveRecord(hallo)
            .compose(new ThreadTransformer<String>())
            .subscribe(new CommonObserver<String>() {
                @Override
                public void onNext(String response) {
                    System.out.println(response);//返回了"1"
                    // 更新主页和记录页面
                    updateRecords();
                    System.out.println("ok");
                    byte[] pic1=PictureUtil.Bitmap2Bytes(bitmap);
                    Intent intent = new Intent(PhotoShowt.this,FoodShare.class);
                    intent.putExtra("typeShare",typePhoto+1);
                    intent.putExtra("pic1",pic1);
                    intent.putExtra("name",nameAll);
                    Integer num=cal1+cal2+cal3+cal4;
                    Double calll=Double.valueOf(num.toString());
                    intent.putExtra("cal1",calll);
                    intent.putExtra("protein1",prote1+prote2+prote3+prote4);
                    intent.putExtra("fat1",fat1+fat2+fat3+fat4);
                    intent.putExtra("carbo1",carbo1+carbo2+carbo3+carbo4);
                    sharem_sure.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                    finish();
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
     list_food.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
             AlertDialog.Builder builder=new AlertDialog.Builder(PhotoShowt.this);
             builder.setMessage("选择您要执行的操作");
             builder.setTitle("提示");
             builder.setPositiveButton("删除条目", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     if(fdata.remove(position)!=null){
                         list_food.setAdapter(fAdapter);

                     }else {
                         System.out.println("failed");
                     }
                 }
             });
             builder.setNegativeButton("修改菜名", new DialogInterface.OnClickListener() {

                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     final String[] list = {
                             "菠萝咕老肉","炒白菜","炒菠菜","炒冬瓜","炒海带","炒河粉","炒花菜","炒豇豆","炒芹菜", "炒青菜",
                             "炒乌冬面", "炒西兰花","炒玉米","蛋包饭","蛋饺","东坡肉","番茄炒蛋","贡丸汤","汉堡","红烧大排",
                             "黄焖鸡米饭","馄饨", "酒酿丸子","咖喱猪排饭","烤鸭","烤鱼","凉拌黄瓜","卤鸡腿","卤肉饭","麻辣豆腐",
                             "毛血旺","梅菜扣肉","米饭","披萨","青椒腊肉", "三杯鸡饭","上海炒面","烧茄子","生煎","酸菜牛肉面",
                             "酸菜鱼","酸汤肥牛","汤包","汤圆","糖醋里脊","糖醋排骨","铁板牛排意面", "土豆丝","西红柿鸡蛋面","鸭血粉丝汤",
                             "扬州炒饭","意面","油爆虾","鱼香肉丝","炸鸡块","炸酱面","炸小鸡腿","蒸鸡蛋","煮鸡蛋"
                     };
                     final ListPopupWindow listPopupWindow;
                     listPopupWindow = new ListPopupWindow(PhotoShowt.this);
                     listPopupWindow.setAdapter(new ArrayAdapter<String>(PhotoShowt.this,android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
                     listPopupWindow.setAnchorView(list_food);//以哪个控件为基准，在该处以mEditText为基准
                     listPopupWindow.setModal(true);

                     listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
                         @Override
                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                             //show1name.setText(list[i]);//把选择的选项内容展示在EditText上
                             FoodItem itemcname=fdata.get(position);
                             itemcname.setFoodname(list[i]);
                             fdata.set(position,itemcname);
                             list_food.setAdapter(fAdapter);
                             listPopupWindow.dismiss();//如果已经选择了，隐藏起来
                         }
                     });
                     listPopupWindow.show();//把ListPopWindow展示出来


                 }
             });
             builder.create().show();
             return false;
         }
     });
//        list_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = (String) ((TextView) view.findViewById(R.id.foodimuch)).getText();
//                 String showText = "点击第" + position + "项，文本内容为：" + 100 + "，ID为：" + id;
//                 System.out.println(showText);
//            }
//            });
//        list_food.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.setHeaderTitle("长按菜单-ContextMenu");
//                menu.add(0, 0, 0, "删除审批单");
//
//                menu.add(0, 1, 0, "取消删除");
//            }
//        });
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
        if(!f1.equals("苑齐超不知道")){
            fdata.add(food1);
        }
        FoodItem food2= new FoodItem();
        food2.setBitmap(b2);
        food2.setWeight(100);
        food2.setFoodname(f2);
        if(!f2.equals("苑齐超不知道")){
            fdata.add(food2);
        }
        FoodItem food3 = new FoodItem();
        food3.setBitmap(b3);
        food3.setWeight(100);
        food3.setFoodname(f3);
        if(!f3.equals("苑齐超不知道")){
            fdata.add(food3);
        }
        FoodItem food4 = new FoodItem();
        food4.setBitmap(b4);
        food4.setWeight(100);
        food4.setFoodname(f4);
        if(!f4.equals("苑齐超不知道")){
            fdata.add(food4);
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