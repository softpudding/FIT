package com.example.fitmvp.presenter;

import android.text.TextUtils;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.LoginUserBean;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.model.LoginModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.LoginActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {
    private LoginModel loginModel = new LoginModel();

    public boolean checkNull() {
        boolean isNull = false;
        if (TextUtils.isEmpty(getIView().getAccount())) {
            getIView().setAccountError("账号不能为空");
            isNull = true;
        }
        else if (TextUtils.isEmpty(getIView().getPassword())) {
            getIView().setPwError("密码不能为空");
            isNull = true;
        }
        return isNull;
    }

    @Override
    public void login(final String account,final String password){
        if (!checkNull()) {
            loginModel.login(account, password, new LoginContract.Model.InfoHint() {
                @Override
                public void successInfo(final LoginUserBean user) {
                    // 登录成功后在JMessage中也进行登录
                    JMessageClient.login(account, password, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0){
                                // 登录成功，在本地保存用户信息
                                loginModel.saveUser(user, new LoginContract.Model.InfoHint() {
                                    @Override
                                    public void successInfo(LoginUserBean user) {
                                    }

                                    @Override
                                    public void loginSuccess() {
                                        // 页面跳转
                                        ToastUtil.setToast("登录成功");
                                        getIView().loginSuccess();
//                                        // 初始化好友列表
//                                        friendModel.initFriendList();
                                    }

                                    @Override
                                    public void errorInfo(String str) {
                                    }

                                    @Override
                                    public void failInfo(String str) {
                                    }
                                });
                            }
                            else{
                                getIView().loginFail("登录失败",responseMessage);
                            }
                        }
                    });
                }

                @Override
                public void loginSuccess() {
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
        else{
            getIView().setButton();
        }
    }
}
