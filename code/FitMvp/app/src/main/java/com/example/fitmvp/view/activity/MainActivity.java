package com.example.fitmvp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;


import android.view.Menu;
import android.view.MenuItem;

import com.example.fitmvp.R;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.draw.BottomBar;
import com.example.fitmvp.view.fragment.FragmentRecord;
import com.example.fitmvp.view.fragment.friends.FragmentFriend;
import com.example.fitmvp.view.fragment.FragmentMe;
import com.example.fitmvp.view.fragment.FragmentMainpage;
import com.example.fitmvp.contract.FriendSearchContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static com.example.fitmvp.view.activity.PhotoPass.hasSdcard;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String CROP_IMAGE_FILE_NAME = "user_crop.jpg";
    private static final String IMAGE_FILE_NAME = "output_user.jpg";
    private BottomBar bottomBar;
    Bitmap bitmap;
    private final int PERMISSION_READ =1;//读取权限
    private final int PERMISSION_READ_AND_CAMERA =0;//读和相机权限
    // 裁剪后图片的宽(X)和高(Y),的正方形。
    private static int output_X = 200;
    private static int output_Y = 200;
    private String mExtStorDir;
    private Uri mUriPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExtStorDir = Environment.getExternalStorageDirectory().toString();

        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(FragmentMainpage.class,
                        "首页",
                        R.drawable.item_home,
                        R.drawable.item_home)
                .addItem(FragmentRecord.class,
                        "记录",
                        R.drawable.item_social,
                        R.drawable.item_social)
                .addItem(FragmentFriend.class,
                        "好友",
                        R.drawable.item_friends,
                        R.drawable.item_friends)
                .addItem(FragmentMe.class,
                        "我",
                        R.drawable.item_me,
                        R.drawable.item_me)
                .build();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int id = getIntent().getIntExtra("id", 0);
        // 跳转到对应的fragment
        // 0-mainpage, 1-socail, 2-friendold, 3-me
        bottomBar.switchFragment(id);
    }

    public void checkReadPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission==PackageManager.PERMISSION_DENIED){
            String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,permissions, PERMISSION_READ);
        }else {
            choseHeadImageFromGallery();
        }

    }
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_READ:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choseHeadImageFromGallery();
                }
                break;
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
    private void setImageToHeadView(Intent intent,Bitmap b) {
        File file=stest(b,"userpic");
        //file
        final Bitmap userpic=b;
        JMessageClient.updateUserAvatar(file, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    ToastUtil.setToast("头像修改成功");
                    ImageView imageView=findViewById(R.id.image_photo);
                    imageView.setImageBitmap(userpic);
                }
                else{
                    // 报错
                    ToastUtil.setToast("头像修改失败");
                    LogUtils.e("error",s);
                }
            }
        });

    }

    public File stest(Bitmap b, String name){
        //生成路径
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dirName = "userphoto";
        File appDir = new File(root , dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = name + ".jpg";

        //获取文件
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            MainActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            return file;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        String mLinshi = System.currentTimeMillis() + CROP_IMAGE_FILE_NAME;
        File mFile = new File(mExtStorDir, mLinshi);

        mUriPath = Uri.parse("file://" + mFile.getAbsolutePath());
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
    // 右上角的菜单栏 - 公告
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    // ActionBar 功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notice:
                toNotice();
                break;
        }
        return true;
    }

    // 前往公告页面查看公告
    private void toNotice(){
        Intent intent = new Intent(this, NoticeListActivity.class);
        startActivity(intent);
    }

}
