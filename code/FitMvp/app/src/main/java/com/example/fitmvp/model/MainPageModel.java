package com.example.fitmvp.model;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.exception.ExceptionEngine;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;


import java.util.List;

public class MainPageModel extends BaseModel implements MainPageContract.Model {
    String tel;
    @Override
    public void getCalValue(final calCallback callback) {
        tel = (String)SpUtils.get("phone","");
        if(tel==null || tel.equals("")){
            callback.fail();
        }
        else{
            httpService1.getSumCal(tel)
                    .compose(new ThreadTransformer<JSONObject>())
                    .subscribe(new CommonObserver<JSONObject>() {
                        @Override
                        public void onNext(JSONObject jsonObject) {
                            try {
                                double target = (double)jsonObject.get("standard");
                                double current = jsonObject.getDouble("result");
                                LogUtils.e("target", String.valueOf(target));
                                LogUtils.e("current",String.valueOf(current));
                                callback.success(target,current);
                            }
                            catch (JSONException e){
                                ExceptionEngine.handleException(e);
                            }
                        }

                        @Override
                        protected void onError(ApiException e) {
                            LogUtils.e("onError",e.message);
                        }
                    });
        }
    }

    @Override
    public void getList(final listCallback callback) {
        tel = (String)SpUtils.get("phone","");
        if(tel==null || tel.equals("")){
            callback.fail();
        }
        else{
            httpService1.getFiveRecord(tel)
                    .compose(new ThreadTransformer<List<RecordBean>>())
                    .subscribe(new CommonObserver<List<RecordBean>>() {
                        @Override
                        public void onNext(List<RecordBean> list) {
                            if(list!=null && list.size()>0){
                                callback.success(list);
                            }
                            else{
                                callback.empty();
                            }
                        }

                        @Override
                        protected void onError(ApiException e) {
                            LogUtils.e("onError",e.message);
                            callback.fail();
                        }
                    });
        }
    }
}
