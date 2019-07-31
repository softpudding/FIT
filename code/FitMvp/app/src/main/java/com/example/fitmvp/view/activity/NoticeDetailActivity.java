package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NoticeDetailActivity extends AppCompatActivity {
    @Bind(R.id.notice_detail_title)
    TextView titleView;
    @Bind(R.id.notice_detail_time)
    TextView timeView;
    @Bind(R.id.notice_detail_msg)
    TextView msgView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("公告");

        setContentView(View.inflate(this, R.layout.notice_detail, null));
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        String msg = intent.getStringExtra("msg");
        titleView.setText(title);
        timeView.setText(time);
        msgView.setText(msg);
    }
}
