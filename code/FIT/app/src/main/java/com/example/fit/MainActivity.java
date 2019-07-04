package com.example.FIT;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
}
