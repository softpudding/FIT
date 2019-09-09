package com.example.fitmvp.contract;

import androidx.annotation.NonNull;

public interface RegisterContract {
    interface Model {
        Boolean register(@NonNull String tel, @NonNull String nickName, @NonNull String password, @NonNull final InfoHint
                infoHint);
        //通过接口产生信息回调
        interface InfoHint {
            void successInfo(String str);
            void errorInfo(String str);
        }
        void getMessage(String tel, @NonNull final InfoHint infoHint);
    }

    interface View {
        String getPhone();
        String getName();
        String getPassword();
        String getPwdAgain();
        Boolean check();
        Boolean checkMsg();
        void registerSuccess(String str);
        void registerFail(String title,String msg);
    }

    interface Presenter {
        void register(String tel,String nickName,String password);
        void sendMsg(String tel);
    }
}
