package com.example.fitmvp.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.chat.activity.ChatActivity;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.presenter.FriendDetailPresenter;
import com.example.fitmvp.utils.LogUtils;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendDetailActivity extends BaseActivity<FriendDetailPresenter> implements FriendContract.View {
    @Bind(R.id.friend_info_photo)
    ImageView photo;
    @Bind(R.id.note_nick)
    TextView noteOrNick;
    @Bind(R.id.friend_info_notename)
    TextView show_notename;
    @Bind(R.id.friend_info_phone)
    TextView show_phone;
    @Bind(R.id.friend_info_nickname)
    TextView show_nickname;
    @Bind(R.id.friend_info_gender)
    TextView show_gender;
    @Bind(R.id.friend_info_birthday)
    TextView show_birthday;
    @Bind(R.id.button_add_chat)
    Button action;
    @Bind(R.id.button_aggre_refuse)
    LinearLayout linearLayout;
    @Bind(R.id.button_agree)
    Button agree;
    @Bind(R.id.button_refuse)
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

    private String phone;
    private String nickName;
    private String noteName;
    private String avatar;
    private String gender;
    private String birthday;

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

        intent = getIntent();
        isFriend = intent.getBooleanExtra("isFriend",false);

        String title;
        if(isFriend){
            title = "详细信息";
        }
        else{
            title = "添加好友";
        }
        actionbar.setTitle(title);
    }

    // 右上角的菜单栏 - 进入好友信息界面
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friendmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 如果是好友，显示右上角的菜单栏
        if(isFriend){
            menu.findItem(R.id.set_notename).setVisible(true);
            menu.findItem(R.id.delete_friend).setVisible(true);
            try{
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
            }
        }
        // 如果不是好友，不显示右上角的菜单栏
        else{
            menu.findItem(R.id.set_notename).setVisible(false);
            menu.findItem(R.id.delete_friend).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // ActionBar 功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://actionbar的左侧图标的点击事件处理
                onBackPressed();
                break;
            case R.id.set_notename:
                toSetNoteName();
                break;
            case R.id.delete_friend:
                deleteFriend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //注册刷新Fragment数据的方法
        registerReceiver();

        phone = intent.getStringExtra("phone");
        // 所有需要的参数都被传入
        if(phone != null){
            nickName = intent.getStringExtra("nickname");
            noteName = intent.getStringExtra("notename");
            avatar = intent.getStringExtra("avatar");
            gender = intent.getStringExtra("gender");
            birthday = intent.getStringExtra("birthday");
            button_type = intent.getIntExtra("buttonType",0);
        }
        // 只传入用户名，由聊天界面进入好友信息界面
        else{
            // isFriend = intent.getBooleanExtra("isFriend",false);
            isFriend = true;
            phone = intent.getStringExtra("TargetId");
            FriendEntry friendEntry = FriendEntry.getFriend(BaseApplication.getUserEntry(),
                    phone,BaseApplication.getAppKey());
            if(friendEntry!=null){
                nickName = friendEntry.nickName;
                noteName = friendEntry.noteName;
                avatar = friendEntry.avatar;
                gender = friendEntry.gender;
                birthday = friendEntry.birthday;
            }
            button_type = 1;
        }

        // 显示头像
        if(avatar!=null && !avatar.equals("")){
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
            newIntent.putExtra("targetUser",phone);
            startActivity(newIntent);
        }
        else{
            // 跳转至聊天界面
            Intent newIntent = new Intent(FriendDetailActivity.this, ChatActivity.class);
            String title = noteName;
            if (TextUtils.isEmpty(title)) {
                title = nickName;
                if (TextUtils.isEmpty(title)) {
                    title = phone;
                }
            }
            newIntent.putExtra(BaseApplication.CONV_TITLE, title);
            newIntent.putExtra(BaseApplication.TARGET_ID, phone);
            newIntent.putExtra(BaseApplication.TARGET_APP_KEY, BaseApplication.getAppKey());
            startActivity(newIntent);
        }
    }

    //发送刷新好友列表的广播
    public void updateFriendList(){
        Intent friendIntent = new Intent("updateFriendList");
        LocalBroadcastManager.getInstance(FriendDetailActivity.this).sendBroadcast(friendIntent);
    }

    // 发送刷新聊天记录的广播
    public void updateMsgList(){
        Intent msgIntent = new Intent("updateMsgList");
        LocalBroadcastManager.getInstance(FriendDetailActivity.this).sendBroadcast(msgIntent);
    }

    // 发送刷新验证消息的广播
    public void updateRecommend(){
        Intent intent = new Intent("updateRecommend");
        LocalBroadcastManager.getInstance(FriendDetailActivity.this).sendBroadcast(intent);
    }

    // 跳转至修改备注名的页面
    private void toSetNoteName(){
        Intent newIntent = new Intent();
        if(!noteName.equals("")){
            noteName = show_notename.getText().toString().trim();
        }
        newIntent.putExtra("noteName",noteName);
        newIntent.putExtra("userName",phone);
        newIntent.setClass(FriendDetailActivity.this, FriendSettingActivity.class);
        startActivity(newIntent);
    }

    private void deleteFriend(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendDetailActivity.this);
        builder.setTitle("删除好友");
        builder.setMessage("将好友 “"+nickName+"”删除，将同时删除与该好友的聊天记录");
        builder.setCancelable(true);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deleteFriend(phone);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LogUtils.e("delete_friend","cancel");
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private LocalBroadcastManager broadcastManager;

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateFriendNoteName");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    // 接收广播，刷新好友备注名
    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            if ("updateFriendNoteName".equals(action)) {
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        //在这里来写你需要刷新的地方
                        String newNoteName = intent.getStringExtra("newNoteName");
                        show_notename.setText(newNoteName);
                    }
                });
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
}
