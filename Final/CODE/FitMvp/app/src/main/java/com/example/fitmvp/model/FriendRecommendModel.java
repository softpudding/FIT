package com.example.fitmvp.model;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.FriendRecommendContract;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.utils.UserUtils;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendRecommendModel extends BaseModel implements FriendRecommendContract.Model {
    private UserEntry user = BaseApplication.getUserEntry();
    /*
     * 根据事件种类修改或新增对应的验证消息记录
     * 更新数据库，更新未读消息数
     */
    public void addRecommend(String fromUsername, final String reason, final ContactNotifyEvent.Type type){
        // 先从数据库中找记录
        FriendRecommendEntry recommendEntry = FriendRecommendEntry.getEntry(user,fromUsername,user.appKey);
        // 如果有，更新消息记录
        if(recommendEntry!=null){
            LogUtils.e("handle_event","update recommendEntry "+recommendEntry.reason);
            String state = UserUtils.getState(type);
            recommendEntry.state = state;
            recommendEntry.reason = reason;
            recommendEntry.save();
        }
        // 如果没有，新增一条记录
        else{
            LogUtils.e("handle_event","new recommendEntry from "+fromUsername);
            JMessageClient.getUserInfo(fromUsername, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo friend) {
                    if(i==0){
                        String otherState = UserUtils.getState(type);
                        LogUtils.e("handle_event","new recommendEntry with user: "+friend.getNickname());
                        FriendRecommendEntry newEntry = new FriendRecommendEntry(friend.getUserID(),
                                friend.getUserName(), friend.getNotename(), friend.getNickname(),
                                friend.getAppKey(), null, friend.getDisplayName(),
                                reason, otherState ,user);
                        newEntry.save();
                        LogUtils.e("find_user",friend.getUserName());
                        SpUtils spUtils = new SpUtils();
                        Integer cacheNum = spUtils.getCachedNewFriendNum() + 1;
                        LogUtils.e("cacheNum",cacheNum.toString());
                        spUtils.setCachedNewFriendNum(cacheNum);
                    }
                    else{
                        LogUtils.e("getUserInfo Fail",s);
                        ToastUtil.setToast(s);
                    }
                }
            });
            LogUtils.e("onEvent","end");
        }
    }

    /*
     * 同意或拒绝好友请求后更新数据库中的记录
     */
    public void updateRecommend(String username,String state){

        FriendRecommendEntry recommendEntry = FriendRecommendEntry.getEntry(user,username,user.appKey);
        if(recommendEntry!=null){
            LogUtils.d("find_recommend","got it");
            recommendEntry.state = state;
            recommendEntry.save();
        }
        else{
            LogUtils.e("find_recommend","don't exist");
        }
    }
}
