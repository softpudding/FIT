package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.ChangePwContract;
import com.example.fitmvp.model.ChangePwModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.ChangePwActivity;

import java.util.HashMap;

public class ChangePwPresenter extends BasePresenter<ChangePwActivity> implements ChangePwContract.Presenter {
    private ChangePwModel model = new ChangePwModel();
    @Override
    public void changePw(String tel,String password){

        if(getIView().check()) {
            model.changePw(tel, password, new ChangePwContract.Model.InfoHint() {
                        @Override
                        public void successInfo(String str) {
                            getIView().changeSuccess(str);
                        }

                        @Override
                        public void errorInfo(String str) {
                            LogUtils.e("LoginPresenter.failInfo", str);
                            getIView().changeFail("错误", str); // 错误
                        }
                    });
        }
    }
}
