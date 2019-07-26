package com.example.fitmvp.presenter;

import android.app.Activity;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.model.SettingModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
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

    @Override
    public void updateInfo(String tel, final String nickname, final String birthday,
                           final String gender, final String height, final String weight) {
        SettingModel model = (SettingModel) getiModelMap().get("setting");
        model.updateInfo(tel, nickname, birthday, gender, height, weight, new SettingContract.Model.InfoHint() {
            @Override
            public void successInfo() {
                // 保存新数据
                SpUtils.put("nickname",nickname);
                SpUtils.put("birthday",birthday);
                SpUtils.put("gender",gender);
                SpUtils.put("height",height);
                SpUtils.put("weight",weight);
                // 刷新UI
                Intent friendInfoIntent = new Intent("updateUserInfo");
                LocalBroadcastManager.getInstance(getIView()).sendBroadcast(friendInfoIntent);
                ToastUtil.setToast("个人信息保存成功");
            }

            @Override
            public void failInfo() {
                ToastUtil.setToast("修改个人信息失败");
            }
        });
    }
}
