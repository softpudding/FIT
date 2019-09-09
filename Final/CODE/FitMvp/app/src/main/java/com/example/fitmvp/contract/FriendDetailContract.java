package com.example.fitmvp.contract;

public interface FriendDetailContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void acceptInvite(String username);
        void refuseInvite(String username);
        void deleteFriend(String username);
    }
}
