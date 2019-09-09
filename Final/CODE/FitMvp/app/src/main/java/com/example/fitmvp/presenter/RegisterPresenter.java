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
    private RegisterModel model = new RegisterModel();

    @Override
    public void register(final String tel, final String nickName, final String password){
        if (getIView().check()) {
            model.register(tel, nickName, password, new RegisterContract.Model.InfoHint() {
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

    @Override
    public void sendMsg(String tel){
        // 检查输入
        if(getIView().checkMsg()){
            model.getMessage(tel, new RegisterContract.Model.InfoHint() {
                @Override
                public void successInfo(String str) {
                    getIView().setTargetMsg(str);
                }

                @Override
                public void errorInfo(String str) {

                }
            });
        }
    }
}
