package com.example.fitmvp.presenter;

import android.content.Intent;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.FormBean;
import com.example.fitmvp.contract.ReportChooseContract;
import com.example.fitmvp.model.ReportChooseModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.ReportChooseDateActivity;
import com.example.fitmvp.view.activity.ReportDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ReportChoosePresenter extends BasePresenter<ReportChooseDateActivity>
        implements ReportChooseContract.Presenter {
    private ReportChooseModel model = new ReportChooseModel();
    // 设置默认起止日期，到今天为止最近7天
    @Override
    public void initDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        // 获取当前日期
        Date today = new Date();
        calendar.setTime(today);
        // -6天
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        Date before = calendar.getTime();

        String end = sdf.format(today);
        String start = sdf.format(before);
        getIView().setStart(start);
        getIView().setEnd(end);
    }

    @Override
    public void getReport(final String start, final String end) {
        model.getReport(start, end, new ReportChooseContract.Model.Callback() {
            @Override
            public void success(FormBean formBean) {
                // 获取成功，跳转到详情页面查看报表
                Intent intent = new Intent(getIView(), ReportDetailActivity.class );
                intent.putExtra("start", start);
                intent.putExtra("end",end);
                intent.putExtra("value_cal",formBean.getEat_cal());
                intent.putExtra("value_pro",formBean.getEat_protein());
                intent.putExtra("value_fat",formBean.getEat_fat());
                intent.putExtra("value_carb",formBean.getEat_carbohydrate());
                intent.putExtra("standard_cal",formBean.getStandard_cal());
                intent.putExtra("standard_pro",formBean.getStandard_protein());
                intent.putExtra("standard_fat",formBean.getStandard_fat());
                intent.putExtra("standard_carb",formBean.getStandard_carbohydrate());
                getIView().startActivity(intent);
            }

            @Override
            public void fail() {
                // 获取失败
                ToastUtil.setToast("获取报表失败，请稍后再试");
            }
        });
    }
}
