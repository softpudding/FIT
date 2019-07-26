package com.example.fitmvp.contract;

import androidx.annotation.NonNull;

import com.example.fitmvp.bean.LoginUserBean;

public interface LoginContract {
    interface Model {
        Boolean login(@NonNull String account, @NonNull String password, @NonNull final InfoHint
                infoHint);
        interface InfoHint {
            void successInfo(LoginUserBean user);
            void loginSuccess();
            void errorInfo(String str);
            void failInfo(String str);
        }
        Boolean logout();
    }

    interface View {
        String getAccount();
        String getPassword();
        void loginSuccess();
        void loginFail(String title, String msg);
        void setAccountError(String str);
        void setPwError(String str);
    }

    interface Presenter {
        void login(String account,String password);
    }
}
