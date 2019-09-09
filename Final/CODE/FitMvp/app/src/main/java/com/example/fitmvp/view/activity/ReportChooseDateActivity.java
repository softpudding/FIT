package com.example.fitmvp.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.ReportChooseContract;
import com.example.fitmvp.presenter.ReportChoosePresenter;
import com.fantasy.doubledatepicker.DoubleDateSelectDialog;
// https://github.com/darrenfantasy/DoubleDatePicker.git

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportChooseDateActivity extends BaseActivity<ReportChoosePresenter>
        implements ReportChooseContract.View {
    @Bind(R.id.start_date)
    TextView startDate;
    @Bind(R.id.end_date)
    TextView endDate;
    @Bind(R.id.set_date)
    Button setDate;
    @Bind(R.id.get_report)
    Button getReport;

    private String start;
    private String end;
    private DoubleDateSelectDialog dialog;

    @Override
    protected void setBar() {
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("饮食报表");
    }

    @Override
    protected ReportChoosePresenter loadPresenter() {
        return new ReportChoosePresenter();
    }

    @Override
    protected void initData() {
        mPresenter.initDate();
        String allowedMin = "2000-01-01";
        dialog = new DoubleDateSelectDialog(this,allowedMin,end,end);
        dialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
            @Override
            public void onSelectFinished(String start, String end) {
                setStart(start);
                setEnd(end);
            }
        });
    }

    @Override
    protected void initListener() {
        setDate.setOnClickListener(this);
        getReport.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.report_choice;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.set_date:
                chooseDate();
                break;
            default:
                otherViewClick(view);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        mPresenter.getReport(start, end);
    }

    public void setStart(String start){
        this.start = start;
        startDate.setText(start);
    }
    public void setEnd(String end){
        this.end = end;
        endDate.setText(end);
    }

    private void chooseDate(){
        dialog.show();
    }
}
