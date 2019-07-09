package com.example.fitmvp.contract;

public interface RegisterContract {
    interface Model {
    }

    interface View {
        String getPhone();
        String getName();
        String getPassword();
        String getPwdAgain();
        Boolean check();
        void registerSuccess();
        void registerFail(String title,String msg);
    }

    interface Presenter {
        void register(String tel,String nickName,String password);
    }
}
