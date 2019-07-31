package com.example.fitmvp.view.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.contract.MeContract;
import com.example.fitmvp.presenter.MePresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.LoginActivity;
import com.example.fitmvp.view.activity.MainActivity;
import com.example.fitmvp.view.activity.Notice;
import com.example.fitmvp.view.activity.PhotoShow;
import com.example.fitmvp.view.activity.SettingActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

import static android.app.Activity.RESULT_CANCELED;


public class FragmentMe extends BaseFragment<MePresenter> implements MeContract.View{
    private Button logout;
    private Button toSetting;
    private Button toReport;
    private TextView textNickname;
    private TextView textPhone;
    private Button toNotice;
    private ImageView userpic;
    private TextView textBirthday;
    private TextView textGender;
    private TextView textHeight;
    private TextView textWeight;


   @Override
   protected Integer getLayoutId(){
       return R.layout.me;
   }

   @Override
   protected MePresenter loadPresenter() {
       return new MePresenter();
   }

   @Override
   protected void initData(){}

   @Override
   protected void initView(){
       logout = view.findViewById(R.id.button_logout);
       toSetting = view.findViewById(R.id.button_setting);
       toReport = view.findViewById(R.id.button_report);
       toNotice=view.findViewById(R.id.button_notice);
       textNickname = view.findViewById(R.id.text_nickname);
       textPhone = view.findViewById(R.id.text_account);
       textBirthday = view.findViewById(R.id.text_birthday);
       textGender = view.findViewById(R.id.text_gender);
       textHeight = view.findViewById(R.id.text_height);
       textWeight = view.findViewById(R.id.text_weight);
       userpic=view.findViewById(R.id.image_photo);
       // 设置数据
       setuserpic();
       updateInfo();
       textPhone.setText((String)SpUtils.get("phone","")); // 手机号不会被修改
       //注册刷新Fragment数据的方法
       registerReceiver();
   }

   private void setuserpic(){
       String username = (String)SpUtils.get("phone","");
       JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
           @Override
           public void gotResult(int i, String s, UserInfo userInfo) {
               if(i == 0){
                   userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                       @Override
                       public void gotResult(int i, String s, Bitmap bitmap) {
                           if (i == 0) {
                               userpic.setImageBitmap(bitmap);
                           }else {
                               // 设置为默认头像
                               userpic.setImageResource(R.drawable.default_portrait80);
                           }
                       }
                   });
               }
               else{
                   LogUtils.e("error",s);
               }
           }
       });
   }

   @Override
   protected void initListener(){
       logout.setOnClickListener(this);
       toSetting.setOnClickListener(this);
       toReport.setOnClickListener(this);
       toNotice.setOnClickListener(this);
       userpic.setOnClickListener(this);
   }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_logout:
                mPresenter.logout();
                break;
                case R.id.button_setting:
                toSetting(view);
                break;
                case R.id.button_notice:
                toNotice(view);
                break;
            case R.id.image_photo:
                cuserpic(view);
                break;
        }
    }

    private void toSetting(View view){
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }
    private void toNotice(View view){
        Intent intent = new Intent(getActivity(), Notice.class);
        startActivity(intent);
    }
    private void cuserpic(View view){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.checkReadPermission();
    }



    @Override
    public void toLogin(){
        ToastUtil.setToast("成功退出当前账号");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void updateInfo(){
       String UNSET = "未设置";
       String nickname = (String)SpUtils.get("nickname","");
       if(nickname==null || nickname.equals("")){
           nickname = UNSET;
       }
       textNickname.setText(nickname);
       String birthday = (String)SpUtils.get("birthday","");
       if(birthday==null || birthday.equals("")){
           birthday = UNSET;
       }
       textBirthday.setText(birthday);
       String gender = (String)SpUtils.get("gender","");
       if(gender==null || gender.equals("")){
           gender = UNSET;
       }
       textGender.setText(gender);
       String height = (String)SpUtils.get("height","");
       if(height==null || height.equals("")){
           height = UNSET;
       }
       textHeight.setText(height);
       String weight = (String)SpUtils.get("weight","");
       if(weight==null || weight.equals("")){
           weight = UNSET;
       }
       textWeight.setText(weight);
    }

    private LocalBroadcastManager broadcastManager;

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateUserInfo");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action= intent.getAction();
            if ("updateUserInfo".equals(action)) {
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        //在这里来写你需要刷新的地方
                        updateInfo();
                    }
                });
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        //注销广播
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
}