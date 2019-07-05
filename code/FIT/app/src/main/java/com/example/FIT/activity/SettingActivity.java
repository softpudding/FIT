package com.example.FIT.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.FIT.R;

import android.view.View;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }
    public void backToMe(View view){
        Intent intent = new Intent();
        intent.setClass(SettingActivity.this, MainActivity.class);
        intent.putExtra("id",3);
        startActivity(intent);
    }
}
