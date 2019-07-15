package com.example.FIT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.FIT.R;

public class RecordDetailActivity extends AppCompatActivity {
    TextView titleView;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_record);
        // 返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleView = findViewById(R.id.detail_record_title);
        textView = findViewById(R.id.detail_record_text);
        imageView = findViewById(R.id.detail_record_img);

        Intent intent = getIntent();
        // 获取参数
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int image = intent.getIntExtra("image", R.drawable.icon);


        // 设置参数
        titleView.setText(title);
        textView.setText(text);
        imageView.setImageResource(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
