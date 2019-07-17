package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.friends.FragmentFrdList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendPresenter extends BasePresenter<FragmentFrdList> implements FriendContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new FriendModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }

    @Override
    public void initFriendList(){
        // List<UserInfo> friendList = new ArrayList<>();
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                if (0 == responseCode) {
                    //获取好友列表成功
                    getIView().setFriendList(userInfoList);
                } else {
                    //获取好友列表失败
                    ToastUtil.setToast(responseMessage);
                }
            }
        });
    }
}
