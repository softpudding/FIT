package com.example.fitmvp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitmvp.R;
import com.example.fitmvp.chat.activity.ChatActivity;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.draw.BottomBar;
import com.example.fitmvp.view.fragment.friends.FragmentFriend;
import com.example.fitmvp.view.fragment.FragmentMe;
import com.example.fitmvp.view.fragment.FragmentSocial;
import com.example.fitmvp.view.fragment.FragmentMainpage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;

public class MainActivity extends AppCompatActivity {


    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(FragmentMainpage.class,
                        "首页",
                        R.drawable.item_home,
                        R.drawable.item_home)
                .addItem(FragmentSocial.class,
                        "社交",
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

//    public void onEvent(MessageEvent event) {
//        updateMsgList();
//        LogUtils.e("接收在线消息","...");
//    }
//
//    /**
//     * 接收离线消息
//     *
//     * @param event 离线消息事件
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(OfflineMessageEvent event) {
//        updateMsgList();
//        LogUtils.e("接收离线消息","...");
//    }
//
//    //发送刷新数据的广播
//    public void updateMsgList(){
//        Intent intent = new Intent("updateMsgList");
//        intent.putExtra("refreshInfo", "yes");
//        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
//        this.setResult(Activity.RESULT_OK, intent);//返回页面1
//        this.finish();
//    }
}
