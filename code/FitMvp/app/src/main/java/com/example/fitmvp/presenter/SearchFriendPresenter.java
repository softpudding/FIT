package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.FriendInfo;
import com.example.fitmvp.contract.SearchFriendContract;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.SearchFriendActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class SearchFriendPresenter extends BasePresenter<SearchFriendActivity> implements SearchFriendContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    @Override
    public void search(String phone){
        JMessageClient.getUserInfo(phone, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i == 0){
                    if(userInfo.getUserName().equals(SpUtils.get("phone",""))){
                        ToastUtil.setToast("不能添加自己为好友");
                        return;
                    }
                    FriendInfo friend = new FriendInfo();
                    friend.setFriendInfo(userInfo);
                    // 设置默认值为false
                    friend.setIsFriend(false);
                    // 检查是否已经是好友
                    isFriend(friend);
                    List<FriendInfo> list = new ArrayList<>();
                    list.add(friend);
                    getIView().setSearchList(list);
                    getIView().initAdapter();
                }
                else{
                    ToastUtil.setToast("用户不存在");
                }
            }
        });
    }

    private void isFriend(final FriendInfo friend){
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if(i == 0){
                    for(UserInfo user : list){
                        // 如果在好友列表中，设为true
                        if(friend.getFriendInfo()==user){
                            friend.setIsFriend(true);
                            return;
                        }
                    }
                }
            }
        });
    }
}
