package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.presenter.FriendDetailPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.PictureUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FriendDetailActivity extends BaseActivity<FriendDetailPresenter> implements FriendContract.View {
    @InjectView(R.id.friend_info_photo)
    ImageView photo;
    @InjectView(R.id.note_nick)
    TextView noteOrNick;
    @InjectView(R.id.friend_info_notename)
    TextView notename;
    @InjectView(R.id.friend_info_phone)
    TextView phone;
    @InjectView(R.id.friend_info_nickname)
    TextView nickname;
    @InjectView(R.id.friend_info_gender)
    TextView gender;
    @InjectView(R.id.friend_info_birthday)
    TextView birthday;
    @InjectView(R.id.button_add_chat)
    Button action;

    private Boolean isFriend;
    Intent intent;

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        intent = getIntent();
        isFriend = intent.getBooleanExtra("isFriend",false);
        // 显示头像
        if(intent.getByteArrayExtra("avatar")!=null){
            Bitmap avatar = PictureUtil.Bytes2Bitmap(intent.getByteArrayExtra("avatar"));
            photo.setImageBitmap(avatar);
        }
        else {
            photo.setImageResource(R.drawable.default_portrait80);
        }

        String nickName = intent.getStringExtra("nickname");
        String noteName = intent.getStringExtra("notename");
        // 显示备注或昵称
        // button上的文字 加好友 or 发消息
        if(!isFriend){
            noteOrNick.setText("昵称: ");
            if(nickName!=null){
                notename.setText(nickName);
            }
            else{
                notename.setText("未知");
            }
            action.setText("加好友");
        }
        else{
            noteOrNick.setText("备注: ");
            if(noteName!=null){
                notename.setText(noteName);
            }
            else{
                notename.setText("无备注");
            }
            action.setText("发消息");
        }
        // 显示用户名（手机号）
        if(intent.getStringExtra("phone")!=null){
            phone.setText(intent.getStringExtra("phone"));
        }
        // 显示昵称
        if(nickName!=null){
            nickname.setText(nickName);
        }
        // 显示性别
        if(intent.getStringExtra("gender")!=null){
            gender.setText(intent.getStringExtra("gender"));
        }
        // 显示生日
        Long birth = intent.getLongExtra("birthday",19900101);
            birthday.setText(String.format("%d",birth));
    }

    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        String title;
        if(isFriend){
            title = "详细信息";
        }
        else{
            title = "添加好友";
        }
        actionbar.setTitle(title);
    }

    protected FriendDetailPresenter loadPresenter() {
        return new FriendDetailPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        action.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_info;
    }

    @Override
    protected void otherViewClick(View view) {
        LogUtils.d("isFriend",isFriend.toString());
        // 加好友
        if(!isFriend){
            // 跳转至发送验证消息界面，传好友手机号
            Intent newIntent = new Intent();
            newIntent.setClass(FriendDetailActivity.this, AddFriendActivity.class);
            newIntent.putExtra("targetUser",intent.getStringExtra("phone"));
            startActivity(newIntent);
            // this.finish();
        }
//        // 发消息
//        else{
//
//        }
    }
}
