package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportDetailActivity extends AppCompatActivity {
    @Bind(R.id.date_begin)
    TextView beginView;
    @Bind(R.id.date_end)
    TextView endView;
    @Bind(R.id.value_cal)
    TextView calVal;
    @Bind(R.id.value_pro)
    TextView proVal;
    @Bind(R.id.value_fat)
    TextView fatVal;
    @Bind(R.id.value_ch2o)
    TextView carbVal;
    @Bind(R.id.refer_cal)
    TextView calStandard;
    @Bind(R.id.refer_pro)
    TextView proStandard;
    @Bind(R.id.refer_fat)
    TextView fatStandard;
    @Bind(R.id.refer_ch2o)
    TextView carbStandard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("报表详情");

        setContentView(View.inflate(this, R.layout.report_detail, null));
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        String begin = intent.getStringExtra("start");
        String end = intent.getStringExtra("end");
        double vCal = intent.getDoubleExtra("value_cal",0);
        double vPro = intent.getDoubleExtra("value_pro",0);
        double vFat = intent.getDoubleExtra("value_fat",0);
        double vCarb = intent.getDoubleExtra("value_carb",0);
        double sCal = intent.getDoubleExtra("standard_cal",0);
        double sPro = intent.getDoubleExtra("standard_pro",0);
        double sFat = intent.getDoubleExtra("standard_fat",0);
        double sCarb = intent.getDoubleExtra("standard_carb",0);

        // 设置字体颜色，超标时为红色，正常时为绿色
        int red = getColor(R.color.circleRed);
        int green = getColor(R.color.textGreen);
        if(vCal > sCal){
            calVal.setTextColor(red);
        }
        else {
            calVal.setTextColor(green);
        }
        if(vPro > sPro){
            proVal.setTextColor(red);
        }
        else{
            proVal.setTextColor(green);
        }
        if(vFat > sFat){
            fatVal.setTextColor(red);
        }
        else {
            fatVal.setTextColor(green);
        }
        if(vCarb > sCarb){
            carbVal.setTextColor(red);
        }
        else {
            carbVal.setTextColor(green);
        }

        // 设置文字内容
        beginView.setText(begin);
        endView.setText(end);
        calVal.setText(String.format(Locale.getDefault(), "%.2f", vCal));
        proVal.setText(String.format(Locale.getDefault(), "%.2f", vPro));
        fatVal.setText(String.format(Locale.getDefault(), "%.2f", vFat));
        carbVal.setText(String.format(Locale.getDefault(), "%.2f", vCarb));
        calStandard.setText(String.format(Locale.getDefault(), "%.2f", sCal));
        proStandard.setText(String.format(Locale.getDefault(), "%.2f", sPro));
        fatStandard.setText(String.format(Locale.getDefault(), "%.2f", sFat));
        carbStandard.setText(String.format(Locale.getDefault(), "%.2f", sCarb));
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
}
