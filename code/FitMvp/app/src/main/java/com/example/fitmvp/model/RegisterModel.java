package com.example.fitmvp.model;

import androidx.annotation.NonNull;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;

public class RegisterModel extends BaseModel implements RegisterContract.Model {
    private Boolean isRegister = false;
    @Override
    public Boolean register(@NonNull String tel,@NonNull String nickName, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService.register(tel,nickName,password)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    public void onNext(String flag) {
                        switch(flag){
                            case "1":
                                infoHint.successInfo("注册成功，请登录");
                                isRegister = true;
                                break;
                            case "0":
                                infoHint.errorInfo("注册失败");
                                isRegister = false;
                                break;
                            default:
                                infoHint.errorInfo(flag);
                                isRegister = false;
                                break;
                        }
                    }

                    @Override
                    public void onError(ApiException e){
                        infoHint.errorInfo(e.message);
                        System.err.println("onError: "+ e.getMessage());
                    }
                });
        return isRegister;
    }
}
