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
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void tomain(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Mainuser.class);
        startActivity(intent);
    }
    public void toregister(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,registerActivity.class);
        startActivity(intent);
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
