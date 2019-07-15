package com.example.FIT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.FIT.R;

public class Repassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repassword);
    }

    public void toLogin(View view) {
        Intent intent = new Intent();
        intent.setClass(Repassword.this, LoginActivity.class);
        startActivity(intent);
    }
}
