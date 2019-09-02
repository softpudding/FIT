package com.example.fitmvp.model;

import androidx.annotation.NonNull;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.MyResponse;
import com.example.fitmvp.bean.RegisterUserBean;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;

public class RegisterModel extends BaseModel implements RegisterContract.Model {
    private Boolean isRegister = false;

    @Override
    public Boolean register(@NonNull String tel,@NonNull String nickName, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        httpService1.register(tel,nickName,password)
                .compose(new ThreadTransformer<MyResponse<RegisterUserBean>>())
                .subscribe(new CommonObserver<MyResponse<RegisterUserBean>>() {
                    @Override
                    public void onNext(MyResponse<RegisterUserBean> response) {
                        switch(response.getResult()){
                            case "1":
                                infoHint.successInfo("注册成功，请登录");
                                isRegister = true;
                                break;
                            case "0":
                                infoHint.errorInfo("注册失败，手机号已被注册");
                                isRegister = false;
                                break;
                            default:
                                infoHint.errorInfo(response.getResult());
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

    @Override
    public void getMessage(String tel, @NonNull final InfoHint infoHint){
        httpService1.sendMessage(tel)
                .compose(new ThreadTransformer<String>())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    public void onNext(String response){
                        infoHint.successInfo(response);
                    }
                    @Override
                    public void onError(ApiException e){
                        System.err.println("onError: "+ e.getMessage());
                        ToastUtil.setToast("服务器出错");
                    }
                });
    }
}
