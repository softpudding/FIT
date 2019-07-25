package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.model.SettingModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.view.activity.SettingActivity;

import java.util.HashMap;

public class SettingPresenter extends BasePresenter<SettingActivity> implements SettingContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new SettingModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("setting", models[0]);
        return map;
    }
}
