package com.example.fitmvp.contract;

import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.database.FriendRecommendEntry;

import java.util.List;

public interface FriendRecommendContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        FriendEntry getUser(String username);
        List<FriendRecommendEntry> getRecommendList();
    }
}
