package com.example.fitmvp.bean;

import cn.jpush.im.android.api.model.UserInfo;

public class FriendInfo {
    private UserInfo friendInfo;
    private Boolean isFriend;

    public void setFriendInfo(UserInfo info){
        friendInfo = info;
    }
    public UserInfo getFriendInfo(){
        return friendInfo;
    }

    public void setIsFriend(Boolean flag){
        isFriend = flag;
    }
    public Boolean getIsFriend(){
        return isFriend;
    }
}
