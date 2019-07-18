package com.example.fitmvp.model;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.FriendRecommendContract;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendRecommendModel extends BaseModel implements FriendRecommendContract.Model {
    private UserEntry user = BaseApplication.getUserEntry();
    private UserInfo friend;
    private String invite_received = "请求加为好友";
    private String invite_accepted = "对方已同意";
    private String invite_declined = "对方已拒绝";
    private String contact_deleted = "已被对方删除";

    /*
     * 根据事件种类修改或新增对应的验证消息记录
     * 更新数据库，更新未读消息数
     */
    public void addRecommend(String fromUsername, String reason, ContactNotifyEvent.Type type){
        String state;
        switch (type){
            //收到好友邀请
            case invite_received:
                state = invite_received;
                break;
            //对方接收了你的好友邀请
            case invite_accepted:
                state = invite_accepted;
                break;
            //对方拒绝了你的好友邀请
            case invite_declined:
                state = invite_declined;
                break;
            //对方将你从好友中删除
            case contact_deleted:
                state = contact_deleted;
                break;
            default:
                state = "";
                break;
        }
        FriendRecommendEntry recommendEntry = FriendRecommendEntry.getEntry(user,fromUsername,user.appKey);
        // 更新消息记录
        if(recommendEntry!=null){
            recommendEntry.state = state;
            recommendEntry.reason = reason;
            recommendEntry.save();
        }
        else{
            JMessageClient.getUserInfo(fromUsername, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if(i==0){
                        friend = userInfo;
                    }
                    else{
                        LogUtils.e("getUserInfo Fail",s);
                        ToastUtil.setToast(s);
                    }
                }
            });
            if(friend != null){
                FriendRecommendEntry newEntry = new FriendRecommendEntry(friend.getUserID(),
                        friend.getUserName(), friend.getNotename(), friend.getNickname(),
                        friend.getAppKey(), friend.getAvatar(), friend.getDisplayName(),
                        reason, state ,user);
                newEntry.save();
                Integer cacheNum = SpUtils.getCachedNewFriendNum() + 1;
                SpUtils.setCachedNewFriendNum(cacheNum);
            }
        }
    }
}
