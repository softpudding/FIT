package com.example.fitmvp.presenter;

import android.os.Handler;

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
    public void search(String phone){
        JMessageClient.getUserInfo(phone, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i == 0){
                    SpUtils spUtils = new SpUtils();
                    if(userInfo.getUserName().equals(spUtils.get("phone",""))){
                        ToastUtil.setToast("不能添加自己为好友");
                        return;
                    }
                    final List<UserInfo> list = new ArrayList<>();
                    list.add(userInfo);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            getIView().updateList(list);
                        }
                    });
                }
                else{
                    ToastUtil.setToast("用户不存在");
                }
            }
        });
    }

    @Override
    public Boolean isFriend(UserInfo friend){
        return friend.isFriend();
    }
}
