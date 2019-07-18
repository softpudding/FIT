package com.example.fitmvp.contract;

public interface FriendAddContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void addFriend(String targetUser, String reason);
    }
}
