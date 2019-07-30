package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.FormBean;
import com.example.fitmvp.contract.ReportChooseContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.exception.ExceptionEngine;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportChooseModel extends BaseModel implements ReportChooseContract.Model {
    @Override
    public void getReport(String start, String end, final Callback callback) {
        // 向后端传递截止日期时需要加一天
        String actualEnd = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = sdf.parse(end);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            sDate = calendar.getTime();
            actualEnd = sdf.format(sDate);
        }
        catch (ParseException e){
            LogUtils.e(e.getClass(),e.getMessage());
        }
        String tel = (String)SpUtils.get("phone","");
        LogUtils.e("actual",actualEnd);
        httpService1.getForm(tel,start,actualEnd)
                .compose(new ThreadTransformer<FormBean>())
                .subscribe(new CommonObserver<FormBean>() {
                    @Override
                    public void onNext(FormBean formBean) {
                        callback.success(formBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        LogUtils.e("onError",e.message);
                    }
                });
    }
}
