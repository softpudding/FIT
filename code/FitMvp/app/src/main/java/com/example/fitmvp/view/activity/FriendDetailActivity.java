package com.example.fitmvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.presenter.FriendDetailPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.fragment.friends.FragmentFrdList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendDetailActivity extends BaseActivity<FriendDetailPresenter> implements FriendContract.View {
    @InjectView(R.id.friend_info_photo)
    ImageView photo;
    @InjectView(R.id.note_nick)
    TextView noteOrNick;
    @InjectView(R.id.friend_info_notename)
    TextView show_notename;
    @InjectView(R.id.friend_info_phone)
    TextView show_phone;
    @InjectView(R.id.friend_info_nickname)
    TextView show_nickname;
    @InjectView(R.id.friend_info_gender)
    TextView show_gender;
    @InjectView(R.id.friend_info_birthday)
    TextView show_birthday;
    @InjectView(R.id.button_add_chat)
    Button action;
    @InjectView(R.id.button_aggre_refuse)
    LinearLayout linearLayout;
    @InjectView(R.id.button_agree)
    Button agree;
    @InjectView(R.id.button_refuse)
    Button refuse;

    private Boolean isFriend;
    /*
     * button_type
     * 0 - add friend
     * 1 - send message
     * 2 - agree or refuse
     */
    private Integer button_type;
    Intent intent;

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        intent = getIntent();
        isFriend = intent.getBooleanExtra("isFriend",false);
        String phone = intent.getStringExtra("phone");
        String nickName = intent.getStringExtra("nickname");
        String noteName = intent.getStringExtra("notename");
        String avatar = intent.getStringExtra("avatar");
        String gender = intent.getStringExtra("gender");
        String birthday = intent.getStringExtra("birthday");
        button_type = intent.getIntExtra("buttonType",0);
        // 显示头像
        if(avatar!=null){
            photo.setImageBitmap(BitmapFactory.decodeFile(avatar));
        }
        else{
            JMessageClient.getUserInfo(phone, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if(i == 0){
                        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int i, String s, Bitmap bitmap) {
                                if (i == 0) {
                                    photo.setImageBitmap(bitmap);
                                }else {
                                    // 设置为默认头像
                                    photo.setImageResource(R.drawable.default_portrait80);
                                }
                            }
                        });
                    }
                }
            });
        }

        // 显示备注或昵称
        // button上的文字 加好友 or 发消息
        if(!isFriend){
            noteOrNick.setText("昵称: ");
            if(nickName!=null){
                show_notename.setText(nickName);
            }
            else{
                show_notename.setText("未知");
            }
            action.setText("加好友");
        }
        else{
            noteOrNick.setText("备注: ");
            if(noteName!=null && !noteName.equals("")){
                show_notename.setText(noteName);
            }
            else{
                show_notename.setText("无备注");
            }
            action.setText("发消息");
        }
        // 显示用户名（手机号）
        if(phone!=null && !phone.equals("")){
            show_phone.setText(phone);
        }
        // 显示昵称
        if(nickName!=null && !nickName.equals("")){
            show_nickname.setText(nickName);
        }
        // 显示性别
        if(gender!=null && !gender.equals("")){
            show_gender.setText(gender);
        }
        // 显示生日
        if(birthday!=null && !birthday.equals("")){
            show_birthday.setText(birthday);
        }

        if(button_type==0 || button_type==1){
            linearLayout.setVisibility(View.INVISIBLE);
            action.setVisibility(View.VISIBLE);
        }
        else{
            linearLayout.setVisibility(View.VISIBLE);
            action.setVisibility(View.INVISIBLE);
        }
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
        if(button_type==2){
            agree.setOnClickListener(this);
            refuse.setOnClickListener(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_info;
    }

    @Override
    public void onClick(View view){
        String username = show_phone.getText().toString();
        switch(view.getId()){
            case R.id.button_add_chat:
                otherViewClick(view);
                break;
            case R.id.button_agree:
                // 同意好友请求
                LogUtils.e("accept start",username);
                mPresenter.acceptInvite(username);
                break;
            case R.id.button_refuse:
                // 拒绝好友请求
                LogUtils.e("refuse start",username);
                mPresenter.refuseInvite(username);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        LogUtils.d("isFriend",isFriend.toString());
        // 加好友
        if(!isFriend){
            // 跳转至发送验证消息界面，传好友手机号
            Intent newIntent = new Intent();
            newIntent.setClass(FriendDetailActivity.this, FriendAddActivity.class);
            newIntent.putExtra("targetUser",intent.getStringExtra("phone"));
            startActivity(newIntent);
        }
//        // 发消息
//        else{
//
//        }
    }

    //发送刷新数据的广播
    public void updateFriendList(){
        Intent friendIntent = new Intent("updateFriendList");
        friendIntent.putExtra("refreshInfo", "yes");
        LocalBroadcastManager.getInstance(FriendDetailActivity.this).sendBroadcast(friendIntent);
        this.setResult(Activity.RESULT_OK, friendIntent);//返回页面1
        this.finish();
    }
}
