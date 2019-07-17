package com.example.fitmvp.model;

import androidx.annotation.NonNull;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.ChangePwContract;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;

public class ChangePwModel extends BaseModel implements ChangePwContract.Model {
    private Boolean isSuccess = false;
    @Override
    public Boolean changePw(@NonNull String tel, @NonNull String password, @NonNull final InfoHint
            infoHint){
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService1.changePw(tel, password)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    public void onNext(String flag){
                        switch(flag) {
                            case "1":
                                infoHint.successInfo("修改成功，请登录");
                                isSuccess = true;
                                break;
                            case "0":
                                infoHint.errorInfo("修改失败，账号不存在");
                                break;
                            default:
                                infoHint.errorInfo(flag);
                                break;
                        }
                    }

                    @Override
                    public void onError(ApiException e){
                        infoHint.errorInfo(e.message);
                        System.err.println("onError: "+ e.getMessage());
                    }
                });
        return isSuccess;
    }
}
