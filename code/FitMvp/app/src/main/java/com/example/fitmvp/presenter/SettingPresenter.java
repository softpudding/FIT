package com.example.fitmvp.presenter;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.model.SettingModel;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.SettingActivity;


public class SettingPresenter extends BasePresenter<SettingActivity> implements SettingContract.Presenter {
    private SettingModel model = new SettingModel();

    @Override
    public void updateInfo(String tel, final String oldNickname, final String nickname, final String birthday,
                           final String gender, final String height, final String weight) {
        model.updateInfo(tel, nickname, birthday, gender, height, weight, new SettingContract.Model.InfoHint() {
            @Override
            public void successInfo() {
                SpUtils spUtils = new SpUtils();
                // 保存新数据
                spUtils.put("nickname",nickname);
                spUtils.put("birthday",birthday);
                spUtils.put("gender",gender);
                spUtils.put("height",height);
                spUtils.put("weight",weight);
                // 刷新UI
                Intent friendInfoIntent = new Intent("updateUserInfo");
                LocalBroadcastManager.getInstance(getIView()).sendBroadcast(friendInfoIntent);
                ToastUtil.setToast("个人信息保存成功");
                // 新旧昵称相同时，修改年龄、性别、身高或体重的信息，需要刷新首页能量环形图的标准值
                if(oldNickname.equals(nickname)){
                    Intent calIntent = new Intent("updateCal");
                    LocalBroadcastManager.getInstance(getIView()).sendBroadcast(calIntent);
                }
                getIView().showButton();
            }

            @Override
            public void failInfo() {
                ToastUtil.setToast("修改个人信息失败");
                getIView().showButton();
            }
        });
    }
}
