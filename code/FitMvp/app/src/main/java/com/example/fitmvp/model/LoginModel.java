package com.example.fitmvp.model;

import androidx.annotation.NonNull;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.SpUtils;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private Boolean isLogin = false;
    @Override
    public Boolean login(@NonNull String account, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService.login(account, password)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    // 请求成功返回后检查登录结果
                    @Override
                    public void onNext(String flag) {
                        switch(flag){
                            case "100":
                                infoHint.successInfo();
                                // 将登录后获取的用户信息存入内存（SharedPreferences）
                                saveUser();
                                isLogin = true;
                                break;
                            case "101":
                                infoHint.failInfo("账号不存在");
                                isLogin = false;
                                break;
                            case "102":
                                infoHint.failInfo("密码不正确");
                                isLogin = false;
                                break;
                            case "103":
                                infoHint.failInfo("账号被禁用");
                                isLogin = false;
                                break;
                            default:
                                infoHint.failInfo(flag);
                                isLogin = false;
                                break;
                        }
                    }

                    @Override
                    public void onError(ApiException e){
                        infoHint.errorInfo(e.message);
                        System.err.println("onError: "+ e.getMessage());
                    }
                });
        return isLogin;
    }

    private void saveUser(){
        // 登录状态设为true
        SpUtils.put("isLogin",true);
        // 保存账号、昵称、头像、生日、身高、体重、性别信息
    }
}
