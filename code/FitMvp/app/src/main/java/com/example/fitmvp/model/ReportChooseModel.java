package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.FormBean;
import com.example.fitmvp.contract.ReportChooseContract;
import com.example.fitmvp.exception.ApiException;
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
        SpUtils spUtils = new SpUtils();
        String tel = (String)spUtils.get("phone","");
        httpService1.getForm(tel,start,end)
                .compose(new ThreadTransformer<FormBean>())
                .subscribe(new CommonObserver<FormBean>() {
                    @Override
                    public void onNext(FormBean formBean) {
                        callback.success(formBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        LogUtils.e("onError",e.message);
                        callback.fail();
                    }
                });
    }
}
