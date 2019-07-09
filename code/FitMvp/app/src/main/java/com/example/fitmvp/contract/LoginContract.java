package com.example.fitmvp.contract;

public interface LoginContract {
    interface Model {
    }

    interface View {
        String getAccount();
        String getPassword();
        void loginSuccess();
        void loginFail(String title, String msg);
    }

    interface Presenter {
        void login(String account,String password);
    }
}
