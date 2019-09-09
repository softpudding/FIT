package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.WelcomeContract;
import com.example.fitmvp.model.WelcomeModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.mvp.IView;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.view.activity.WelcomeActivity;

import java.util.HashMap;

public class WelcomePresenter extends BasePresenter<WelcomeActivity> implements WelcomeContract.Presenter {
    @Override
    public void jump(SpUtils spUtils, WelcomeContract.View view){
        WelcomeModel welcomeModel = new WelcomeModel();
        Boolean isLogin = welcomeModel.isLogin(spUtils);
        System.out.println(isLogin);
        // 已登录则直接进入主页
        if(isLogin){
            view.toMainPage();
        }
        // 未登录，进入登录界面
        else{
            view.toLogin();
        }
    }
}
