package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.model.RegisterModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.RegisterActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

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
    public void register(final String tel, final String nickName, final String password){
        if (getIView().check()) {
            ((RegisterModel) getiModelMap().get("register"))
                    .register(tel, nickName, password, new RegisterContract.Model.InfoHint() {
                        @Override
                        public void successInfo(String str) {
                            // 注册成功后，用同样的账号密码在JMessage上注册
                            final String message = str;
                            RegisterOptionalUserInfo optionalUserInfo = new RegisterOptionalUserInfo();
                            optionalUserInfo.setNickname(nickName);
                            JMessageClient.register(tel, password, optionalUserInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    // 注册成功
                                    if(i==0){
                                        getIView().registerSuccess(message);
                                    }
                                    // 注册失败
                                    else{
                                        getIView().registerFail("注册失败",s);
                                    }
                                }
                            });
                        }

                        @Override
                        public void errorInfo(String str) {
                            LogUtils.e("LoginPresenter.failInfo", str);
                            getIView().registerFail("注册失败", str); // 错误
                        }
                    });
        }
    }
}
