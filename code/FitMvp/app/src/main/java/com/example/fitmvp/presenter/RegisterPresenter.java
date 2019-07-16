package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.model.RegisterModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.RegisterActivity;

import java.util.HashMap;

public class RegisterPresenter extends BasePresenter<RegisterActivity> implements RegisterContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new RegisterModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("register", models[0]);
        return map;
    }

    @Override
    public void register(String tel,String nickName,String password){
        if (getIView().check()) {
            ((RegisterModel) getiModelMap().get("register"))
                    .register(tel, nickName, password, new RegisterContract.Model.InfoHint() {
                        @Override
                        public void successInfo(String str) {
                            getIView().registerSuccess(str);  //成功
                        }

                        @Override
                        public void errorInfo(String str) {
                            LogUtils.e("LoginPresenter.failInfo", str);
                            getIView().registerFail("错误", str); // 错误
                        }
                    });
        }
    }
}
