package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendSearchContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.FriendSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendSearchPresenter extends BasePresenter<FriendSearchActivity> implements FriendSearchContract.Presenter {
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
                    List<UserInfo> list = new ArrayList<>();
                    list.add(userInfo);
                    getIView().setSearchList(list);
                    getIView().initAdapter();
                }
                else{
                    ToastUtil.setToast("用户不存在");
                }
            }
        });
    }

    @Override
    public Boolean isFriend(UserInfo friend){
        FriendEntry friendEntry = FriendEntry.getFriend(friend.getUserID());
        if(friendEntry == null){
            return false;
        }
        else{
            return true;
        }
    }
}
