package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordDetailActivity extends AppCompatActivity {
    @Bind(R.id.detail_record_title)
    TextView titleView;
    @Bind(R.id.detail_record_timestamp)
    TextView timeView;
    @Bind(R.id.detail_record_weight)
    TextView weightView;
    @Bind(R.id.detail_cal)
    TextView calView;
    @Bind(R.id.detail_pro)
    TextView proView;
    @Bind(R.id.detail_fat)
    TextView fatView;
    @Bind(R.id.detail_ch2o)
    TextView carbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_detail);
        ButterKnife.bind(this);
        // 返回键
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("记录");

        Intent intent = getIntent();
        // 获取参数
        String food = intent.getStringExtra("food");
        String time = intent.getStringExtra("timeStamp");
        String weight = intent.getStringExtra("weight");
        String cal = intent.getStringExtra("cal");
        String pro = intent.getStringExtra("pro");
        String fat = intent.getStringExtra("fat");
        String carb = intent.getStringExtra("carbohydrate");

        // 设置参数
        titleView.setText(food);
        timeView.setText(time);
        weightView.setText(weight);
        calView.setText(cal);
        proView.setText(pro);
        fatView.setText(fat);
        carbView.setText(carb);

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
