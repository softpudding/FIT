package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.WelcomeContract;
import com.example.fitmvp.model.WelcomeModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.view.activity.WelcomeActivity;

import java.util.HashMap;

public class WelcomePresenter extends BasePresenter<WelcomeActivity> implements WelcomeContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new WelcomeModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("welcome", models[0]);
        return map;
    }

    @Override
    public void jump(){
        WelcomeModel welcomeModel = (WelcomeModel)getiModelMap().get("welcome");
        Boolean isLogin = welcomeModel.isLogin();
        System.out.println(isLogin);
        // 已登录则直接进入主页
        if(isLogin){
            getIView().toMainPage();
        }
        // 未登录，进入登录界面
        else{
            getIView().toLogin();
        }
    }
}
