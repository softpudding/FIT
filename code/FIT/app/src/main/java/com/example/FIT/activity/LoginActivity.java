package com.example.FIT.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.FIT.R;

import android.view.View;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void toMain(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        intent.putExtra("id",0);
        startActivity(intent);
    }
    public void toRegister(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void toRepassword(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, Repassword.class);
        startActivity(intent);
    }
}
