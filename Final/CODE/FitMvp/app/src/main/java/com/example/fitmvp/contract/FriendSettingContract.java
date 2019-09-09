package com.example.fitmvp.contract;

public interface FriendSettingContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void setNoteName(String newNoteName, String userName);
    }
}
