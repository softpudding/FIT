package com.example.fit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fit.fragment.Fragment_mainpage;
import com.example.fit.fragment.Fragment_me;
import com.example.fit.fragment.Fragment_social;
import com.example.fit.fragment.Fragment_friends;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                // 这里希望选中前后的icon大小不同，但是用不同大小的png没有效果
                .addItem(Fragment_mainpage.class,
                        "首页",
                        R.drawable.item_home_before,
                        R.drawable.item_home_after)
                .addItem(Fragment_social.class,
                        "社交",
                        R.drawable.item_social_before,
                        R.drawable.item_social_after)
                .addItem(Fragment_friends.class,
                        "好友",
                        R.drawable.item_friends_befoer,
                        R.drawable.item_friends_after)
                .addItem(Fragment_me.class,
                        "我",
                        R.drawable.item_me_before,
                        R.drawable.item_me_after)
                .build();
    }
}
