package com.example.fitmvp.contract;

import androidx.annotation.NonNull;

public interface ChangePwContract {
    interface Model {
        Boolean changePw(@NonNull String tel, @NonNull String password, @NonNull final InfoHint
                infoHint);
        interface InfoHint{
            void successInfo(String str);
            void errorInfo(String str);
        }
    }

    interface View {
        String getAccount();
        String getPassword();
        String getPwdAgain();
        String getMsg();
        Boolean check();
        Boolean checkMsg();
        void changeSuccess(String str);
        void changeFail(String title, String msg);
    }

    interface Presenter {
        void changePw(String tel,String password);
        void sendMsg(String tel);
    }
}
