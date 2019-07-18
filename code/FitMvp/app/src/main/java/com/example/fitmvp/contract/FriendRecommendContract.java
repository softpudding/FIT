package com.example.fitmvp.contract;

import com.example.fitmvp.database.FriendEntry;

public interface FriendRecommendContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        FriendEntry getUser(String username);
        void getRecommendList();
    }
}
