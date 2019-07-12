package com.example.fitmvp.contract;

public interface WelcomeContract {
    interface Model {
        Boolean isLogin();
    }

    interface View {
        void toLogin();
        void toMainPage();
    }

    interface Presenter {
        void jump();
    }
}
