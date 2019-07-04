package com.example.FIT.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.FIT.BottomBar;
import com.example.FIT.R;
import com.example.FIT.fragment.Fragment_mainpage;
import com.example.FIT.fragment.Fragment_me;
import com.example.FIT.fragment.Fragment_social;
import com.example.FIT.fragment.Fragment_friends;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                // 这里希望选中前后的icon大小不同，但是用不同大小的png没有效果
                .addItem(Fragment_mainpage.class,
                        "首页",
                        R.drawable.item_home,
                        R.drawable.item_home)
                .addItem(Fragment_social.class,
                        "社交",
                        R.drawable.item_social,
                        R.drawable.item_social)
                .addItem(Fragment_friends.class,
                        "好友",
                        R.drawable.item_friends,
                        R.drawable.item_friends)
                .addItem(Fragment_me.class,
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
        // 0-mainpage, 1-socail, 2-friends, 3-me
        bottomBar.switchFragment(id);
    }
}
