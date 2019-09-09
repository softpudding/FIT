package com.example.fitmvp.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.presenter.FriendPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.utils.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendModel extends BaseModel implements FriendContract.Model {
    private List<FriendEntry> mList = new ArrayList<>();
    private List<FriendEntry> forDelete = new ArrayList<>();

    // 同步本地好友列表
    public void initFriendList(final InfoHint infoHint){
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if(i == 0){
                    if(list!=null){
                        LogUtils.e("number of friends",String.format("%d",list.size()));
                    }
                    else{
                        LogUtils.e("number of friends","list is null");
                    }

                    if (list != null && list.size() != 0){
                        ActiveAndroid.beginTransaction();
                        try{
                            for(UserInfo userInfo : list){
                                String displayName = userInfo.getDisplayName();
                                // 下载用户数据
                                JMessageClient.getUserInfo(userInfo.getUserName(), null);
                                // 暂时不涉及到letter
                                String letter = "A";

                                //避免重复请求时导致数据重复A
                                UserEntry user = BaseApplication.getUserEntry();
                                FriendEntry friend = FriendEntry.getFriend(user,
                                        userInfo.getUserName(), userInfo.getAppKey());
                                String gender = UserUtils.getGender(userInfo);
                                String birthday = UserUtils.getBirthday(userInfo);
                                File file = userInfo.getAvatarFile();
                                if (null == friend) {
                                    if (TextUtils.isEmpty(userInfo.getAvatar())) {
                                        friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                null, displayName, letter, gender, birthday, user);
                                    }
                                    else {
                                        if(file==null){
                                            friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                    null, displayName, letter,  gender, birthday, user);
                                        }
                                        else{
                                            friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                    userInfo.getAvatarFile().getAbsolutePath(), displayName, letter,  gender, birthday, user);
                                        }
                                    }
                                }
                                else{
                                    friend.noteName = userInfo.getNotename();
                                    friend.nickName = userInfo.getNickname();
                                    friend.gender = gender;
                                    friend.birthday = birthday;
                                    friend.displayName = displayName;
                                    if(file==null){
                                        friend.avatar = null;
                                    }
                                    else{
                                        friend.avatar = file.getAbsolutePath();
                                    }
                                }
                                friend.save();
                                mList.add(friend);
                                //LogUtils.d("find friend",friend.username);
                                // 是好友，添加到不被删除的列表中
                                forDelete.add(friend);
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                    //其他端删除好友后,登陆时把数据库中的也删掉
                    UserEntry me = BaseApplication.getUserEntry();
                    List<FriendEntry> friends = me.getFriends();
                    // 移出没有被删除的好友
                    friends.removeAll(forDelete);
                    // 剩下的是已经删除的好友，删除数据库中数据
                    for (FriendEntry del : friends) {
                        del.delete();
                        mList.remove(del);
                    }
                    infoHint.updateFriend();
                }
                else{
                    ToastUtil.setToast("获取好友列表失败");
                }
            }
        });
    }

    // 从数据库中获取好友列表
    public List<FriendEntry> getFriendList(){
        return BaseApplication.getUserEntry().getFriends();
    }

    // 添加好友
    @Override
    public void addFriend(String friendname, final InfoHint infoHint){
        final UserEntry user = BaseApplication.getUserEntry();
        FriendEntry friendEntry = FriendEntry.getFriend(user, friendname, user.appKey);
        // 不在数据库中
        if(friendEntry==null){
            JMessageClient.getUserInfo(friendname, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if(i==0){
                        String gender = UserUtils.getGender(userInfo);
                        String birthday = UserUtils.getBirthday(userInfo);
                        // String letter = "A";
                        FriendEntry newFriend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(),
                                userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                userInfo.getAvatar(), userInfo.getDisplayName(), "A", gender, birthday, user);
                        newFriend.save();
                        // 更新页面
                        LogUtils.e("update_list","start");
                        infoHint.updateFriend();
                    }
                    else{
                        LogUtils.e("find user fail",s);
                        ToastUtil.setToast(s);
                    }
                }
            });
        }
    }

    // 删除好友
    public void deleteFriend(String friendname, final InfoHint infoHint){
        UserEntry user = BaseApplication.getUserEntry();
        FriendEntry friendEntry = FriendEntry.getFriend(user, friendname, user.appKey);
        if(friendEntry!=null){
            LogUtils.e("delete_friend","from database");
            friendEntry.delete();
            infoHint.updateFriend();
        }
    }

    public Boolean updateNoteName(String newNoteName, String friendName){
        FriendEntry friendEntry = FriendEntry.getFriend(BaseApplication.getUserEntry(),friendName,BaseApplication.getAppKey());
        if(friendEntry!=null){
            friendEntry.noteName = newNoteName;
            friendEntry.displayName = newNoteName;
            friendEntry.save();
            return true;
        }
        else{
            return false;
        }
    }

}
