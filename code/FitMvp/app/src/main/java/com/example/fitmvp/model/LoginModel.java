package com.example.fitmvp.model;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private boolean isLogin = false;

    public boolean login(@NonNull String account, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService.login(account, password)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    public void onNext(String flag) {
                        switch(flag){
                            case "100":
                                infoHint.successInfo();
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
                        isLogin = false;
                        infoHint.errorInfo(e.message);
                    }
                });
        return isLogin;
    }

    //通过接口产生信息回调
    public interface InfoHint {
        void successInfo();
        void errorInfo(String str);
        void failInfo(String str);
    }
}
