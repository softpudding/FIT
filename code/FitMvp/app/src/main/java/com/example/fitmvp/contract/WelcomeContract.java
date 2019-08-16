package com.example.fitmvp.contract;

import com.example.fitmvp.mvp.IView;
import com.example.fitmvp.utils.SpUtils;

public interface WelcomeContract {
    interface Model {
        Boolean isLogin(SpUtils spUtils);
    }

    interface View {
        void toLogin();
        void toMainPage();
    }

    interface Presenter {
        void jump(SpUtils spUtils, View view);
    }
}
