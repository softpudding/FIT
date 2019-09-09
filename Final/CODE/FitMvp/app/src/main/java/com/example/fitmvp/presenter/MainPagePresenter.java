package com.example.fitmvp.presenter;

import android.os.Handler;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.model.MainPageModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.FragmentMainpage;

import java.util.List;


public class MainPagePresenter extends BasePresenter<FragmentMainpage>
        implements MainPageContract.Presenter {
    private MainPageModel mainPageModel = new MainPageModel();

    @Override
    public void getCalValue() {
        mainPageModel.getCalValue(new MainPageContract.Model.calCallback() {
            @Override
            public void success(double target, double current) {
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
        mainPageModel.getList(new MainPageContract.Model.listCallback() {
            @Override
            public void success(List<RecordBean> list) {
                getIView().updateList(list);
            }

            @Override
            public void fail() {
                ToastUtil.setToast("获取识别记录失败");
            }
        });
    }
}
