package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.MeContract;
import com.example.fitmvp.model.LoginModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.fragment.FragmentMe;

import java.util.HashMap;

public class MePresenter extends BasePresenter<FragmentMe> implements MeContract.Presenter {
    @Override
    public void logout(){
        LoginModel loginModel = (LoginModel) getiModelMap().get("login");
        if(loginModel.logout()){
            LogUtils.e("debug: ","logout return");
            getIView().toLogin();
        }
    }
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
}
