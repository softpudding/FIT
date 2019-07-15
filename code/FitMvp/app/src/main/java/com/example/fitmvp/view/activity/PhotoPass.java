package com.example.fitmvp.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fitmvp.R;

public class PhotoPass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}