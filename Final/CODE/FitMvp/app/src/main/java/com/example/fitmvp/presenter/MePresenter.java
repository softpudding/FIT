package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.MeContract;
import com.example.fitmvp.model.LoginModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.fragment.FragmentMe;

public class MePresenter extends BasePresenter<FragmentMe> implements MeContract.Presenter {
    private LoginModel model = new LoginModel();

    @Override
    public void logout(){
        if(model.logout()){
            LogUtils.e("debug: ","logout return");
            getIView().toLogin();
        }
    }
}
