package com.example.fitmvp.contract;

public interface AddFriendContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void addFriend(String targetUser, String reason);
    }
}
