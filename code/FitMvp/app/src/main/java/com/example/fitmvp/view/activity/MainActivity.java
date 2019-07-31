package com.example.fitmvp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fitmvp.R;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.draw.BottomBar;
import com.example.fitmvp.view.fragment.FragmentRecord;
import com.example.fitmvp.view.fragment.friends.FragmentFriend;
import com.example.fitmvp.view.fragment.FragmentMe;
import com.example.fitmvp.view.fragment.FragmentMainpage;

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
