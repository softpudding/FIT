package com.example.fitmvp.view.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.bean.PhotoTypetBean;
import com.example.fitmvp.bean.box;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.PictureUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class PhotoPassm extends AppCompatActivity {
    private static final String IMAGE_FILE_NAME = "output_image_m.jpg";
    private static final String CROP_IMAGE_FILE_NAME = "fit_crop_m.jpg";
    //    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // 裁剪后图片的宽(X)和高(Y),s是正方形，c是原型。
    private static int output_Xs = 900;
    private static int output_Ys = 630;
    private static int output_Xc = 1000;
    private static int output_Yc = 750;
    //改变头像的标记位
    private ImageView headImage = null;
    private String mExtStorDir;
    private Uri mUriPath;

    //提示跳转
    private TextView notes=null;

    private final int PERMISSION_READ_AND_CAMERA =0;//读和相机权限
    private final int PERMISSION_READ =1;//读取权限
//盘子种类
    private TextView type_name = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_passm);
        mExtStorDir = Environment.getExternalStorageDirectory().toString();
        notes=(TextView)findViewById(R.id.tishi);
        Intent intent = getIntent();
        // 获取盘子参数
        String show_name = intent.getStringExtra("plate_type");//c和s分别代表圆方盘
        notes.setText(show_name);

        Button filem = (Button) findViewById(R.id.openfilem);
        filem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkReadPermission();
            }
        });
        Button cameram=(Button)findViewById(R.id.type_whole);
        cameram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        String savePath = mExtStorDir;
        System.out.println("savePath: "+savePath);// /storage/emulated/0
        Intent intent = null;
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            //设定拍照存放到自己指定的目录,可以先建好
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            Uri pictureUri;
            File pictureFile = new File(savePath, IMAGE_FILE_NAME);
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {//24
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureUri = FileProvider.getUriForFile(this, getPackageName()+".fileProvider", pictureFile);

            }
            else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureUri = Uri.fromFile(pictureFile);
            }
            if (intent != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        pictureUri);
                startActivityForResult(intent, CODE_CAMERA_REQUEST);
            }
        }
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        }
        else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
//                    cropRawPhoto(Uri.fromFile(tempFile));
                    cropRawPhoto(getImageContentUri(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUriPath));
                    setImageToHeadView(intent,bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void checkStoragePermission() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {/*Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,*/Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_READ_AND_CAMERA);
        }
        else {
            choseHeadImageFromCameraCapture();
        }
    }

    private void checkReadPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission==PackageManager.PERMISSION_DENIED){
            String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,permissions, PERMISSION_READ);
        }else {
            choseHeadImageFromGallery();
        }

    }

    //权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_READ_AND_CAMERA:
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i]==PackageManager.PERMISSION_DENIED){
                        Toast.makeText(this, "why ??????", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
//                choseHeadImageFromCameraCapture();
                break;
            case PERMISSION_READ:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choseHeadImageFromGallery();
                }
                break;
        }

    }
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        String tyy=notes.getText().toString();
        if(tyy.equals("圆盘食物")){
            intent.putExtra("aspectX", 4);
            intent.putExtra("aspectY", 3);
            intent.putExtra("outputX", output_Xc);
            intent.putExtra("outputY", output_Yc);
        }
        else{
            intent.putExtra("aspectX", 10);
            intent.putExtra("aspectY", 7);
            intent.putExtra("outputX", output_Xs);
            intent.putExtra("outputY", output_Ys);
        }
        intent.putExtra("return-data", true);

        //startActivityForResult(intent, CODE_RESULT_REQUEST); //直接调用此代码在小米手机有异常，换以下代码
        String mLinshi = System.currentTimeMillis() + CROP_IMAGE_FILE_NAME;
        File mFile = new File(mExtStorDir, mLinshi);
//        mHeadCachePath = mHeadCacheFile.getAbsolutePath();

        mUriPath = Uri.parse("file://" + mFile.getAbsolutePath());
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
     // intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置原图像的View
     */
    private void setImageToHeadView(Intent intent,Bitmap b) {
        try {
            if (intent != null) {
                String tyy=notes.getText().toString();
                if(tyy.equals("圆盘食物")){
                    notes.setText("识别中。。。");
                    passPhoto2(b,2); //传输图像给后端,开始操作

                    System.out.println("圆盘");
                }
                else{
                    notes.setText("识别中。。。");
                    System.out.println(b.getWidth()+"okkkkk"+b.getHeight());
                    passPhoto2(b,1);
                    System.out.println("方盘");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //图片传送接口
    public void passPhoto2(Bitmap bitmap,final Integer integer){
        Integer obj_type=2;
        String pic= PictureUtil.bitmapToBase64(bitmap);
        final byte[] picb=PictureUtil.Bitmap2Bytes(bitmap);
        String tel= BaseApplication.getUserEntry().username;
        System.out.println(bitmap.getWidth()+"hahah"+bitmap.getHeight());
        Intent intent = new Intent(PhotoPassm.this, PhotoShowt.class);
        Integer x=0;
        Integer y=0;
        Integer w=50;
        Integer h=50;

        for(int i=0;i<4;i++){
                            int j=i+1;
                            intent.putExtra("x"+j,x);
                            intent.putExtra("y"+j,y);
                            intent.putExtra("w"+j,w);
                            intent.putExtra("h"+j,h);
                            String foodname="food";
                            intent.putExtra("food"+j,foodname+j);//把菜名放进
                        }
        if(integer==2){notes.setText("圆盘食物");}
                        else{notes.setText("方盘食物");}
        intent.putExtra("type",integer);
                        intent.putExtra("pic",picb);
                        startActivity(intent);
//        Http.getHttpService(2).multifood(tel,obj_type,pic,integer)
//                .compose(new ThreadTransformer<PhotoTypetBean>())
//                .subscribe(new CommonObserver<PhotoTypetBean>() {
//                    // 请求成功返回后检查登录结果
//                    @Override
//                    public void onNext(PhotoTypetBean response) {
//                        System.out.println(response.getBoxes());
//                        System.out.println(response.getPredictions());
//                        Intent intent = new Intent(PhotoPassm.this, PhotoShowt.class);
//                        //JSONArray tbox =response.getBoxes();
//                        JSONArray tpre=response.getPredictions();
//                        JSONObject opre;
//                        System.out.println(tpre);
//                        Integer listSize=tpre.size();
//                        for(int i=0;i<listSize;i++){// 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                                opre = tpre.getJSONObject(i);
//                                String aa=opre.getString("class");
//                                int j=i+1;
//                                intent.putExtra("food"+j,aa);//把菜名放进
//                        }
//                        JSONArray boxes=response.getBoxes();
//                        for(int i=0;i<4;i++){
//                            opre=boxes.getJSONObject(i);
//                            Integer x=opre.getInteger("x");
//                            Integer y=opre.getInteger("y");
//                            Integer w=opre.getInteger("w");
//                            Integer h=opre.getInteger("h");
//                            int j=i+1;
//                            intent.putExtra("x"+j,x);
//                            intent.putExtra("y"+j,y);
//                            intent.putExtra("w"+j,w);
//                            intent.putExtra("h"+j,h);
//                        }
//
////                        JSONArray nutri=response.getNutri();
////                        for(int i=0;i<4;i++){
////                            opre=nutri.getJSONObject(i);
////                            Integer calory=opre.getInteger("calory");
////                            Double protein=opre.getDouble("protein");
////                            Double fat=opre.getDouble("fat");
////                            Double carbohydrate=opre.getDouble("carbohydrate");
////                            int j=i+1;
////                            intent.putExtra("calory"+j,calory);
////                            intent.putExtra("protein"+j,protein);
////                            intent.putExtra("fat"+j,fat);
////                            intent.putExtra("carbohydrate"+j,carbohydrate);
////                            System.out.println(calory+","+protein);
////                        }
//                        intent.putExtra("type",integer);
//                        intent.putExtra("pic",picb);
//                        if(integer==2){notes.setText("圆盘食物");}
//                        else{notes.setText("方盘食物");}
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onError(ApiException e){
//                        System.err.println("onError: "+ e.getMessage());
//                        System.out.println("嘎嘎嘎");
//                        notes.setText("网管断网了。");
//                    }
//                });
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    private Bitmap imageZoom(Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        double maxSize =1000.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /**图片的缩放方法
     * @param bgimage：源图片资源
     * @param newWidth：缩放后宽度
     * @param newHeight：缩放后高度
     * @return */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);

        return bitmap;
    }


}
