package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.RecordContract;
import com.example.fitmvp.model.RecordModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.FragmentRecord;

import java.util.HashMap;
import java.util.List;

public class RecordPresenter extends BasePresenter<FragmentRecord> implements RecordContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new RecordModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("loadValue", models[0]);
        return map;
    }

    @Override
    public void getAllRecords() {
        RecordModel model = (RecordModel)getiModelMap().get("loadValue");
        model.getAllRecords(new RecordContract.Model.Callback() {
            @Override
            public void success(List<RecordBean> list) {
                getIView().updateList(list);
            }

            @Override
            public void fail() {
                ToastUtil.setToast("获取记录失败");
            }
        });
    }
}
