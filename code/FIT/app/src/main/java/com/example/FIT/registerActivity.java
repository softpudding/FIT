package com.example.FIT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class registerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }
    public void tologin(View view){
        Intent intent = new Intent();
        intent.setClass(registerActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
