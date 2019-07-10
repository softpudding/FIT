package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.model.LoginModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.LoginActivity;

import java.util.HashMap;

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }

    @Override
    public void login(String account,String password){
        if (!getIView().checkNull()) {
            ((LoginModel) getiModelMap().get("login"))
                    .login(account, password, new LoginContract.Model.InfoHint() {
                @Override
                public void successInfo() {
                    getIView().loginSuccess();  //成功
                }

                @Override
                public void errorInfo(String str){
                    getIView().loginFail("错误",str); // 错误
                }

                @Override
                public void failInfo(String str) {
                    LogUtils.e("LoginPresenter.failInfo", str);
                    getIView().loginFail("登录失败",str);  //失败
                }
            });
        }
    }
}
