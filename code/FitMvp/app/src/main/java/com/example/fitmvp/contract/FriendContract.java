package com.example.fitmvp.contract;

import com.example.fitmvp.database.FriendEntry;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public interface FriendContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        List<FriendEntry> getFriendList();
    }
}
