package com.example.fitmvp.presenter;

import android.os.Handler;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.model.MainPageModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.FragmentMainpage;

import java.util.HashMap;
import java.util.List;


public class MainPagePresenter extends BasePresenter<FragmentMainpage>
        implements MainPageContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MainPageModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("loadValue", models[0]);
        return map;
    }

    @Override
    public void getCalValue() {
        MainPageModel mainPageModel = (MainPageModel)getiModelMap().get("loadValue");
        mainPageModel.getCalValue(new MainPageContract.Model.calCallback() {
            @Override
            public void success(double target, double current) {
                LogUtils.e("target", String.valueOf(target));
                LogUtils.e("current",String.valueOf(current));
                getIView().setCircleValue(target,current);
            }

            @Override
            public void fail() {
                ToastUtil.setToast("加载数据失败");
            }
        });
    }

    @Override
    public void getList() {
        MainPageModel mainPageModel = (MainPageModel)getiModelMap().get("loadValue");
        mainPageModel.getList(new MainPageContract.Model.listCallback() {
            @Override
            public void success(List<RecordBean> list) {
                getIView().setList(list);
                getIView().updateList();
            }

            @Override
            public void fail() {
                ToastUtil.setToast("获取识别记录失败");
            }

            @Override
            public void empty() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getIView().showEmptyList();
                    }
                });
            }
        });
    }
}
