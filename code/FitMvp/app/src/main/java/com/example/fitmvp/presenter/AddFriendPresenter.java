package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.AddFriendContract;
import com.example.fitmvp.model.AddFriendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.AddFriendActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class AddFriendPresenter extends BasePresenter<AddFriendActivity> implements AddFriendContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new AddFriendModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("addFriend", models[0]);
        return map;
    }

    @Override
    public void search(String phone){
        JMessageClient.getUserInfo(phone, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i == 0){
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
}
