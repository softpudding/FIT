package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendAddContract;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.FriendAddActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

public class FriendAddPresenter extends BasePresenter<FriendAddActivity> implements FriendAddContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    @Override
    public void addFriend(String targetUser, String reason){
        ContactManager.sendInvitationRequest(targetUser, null, reason, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i == 0){
                    ToastUtil.setToast("发送成功，等待对方验证");
                    getIView().goBack();
                }
                else{
                    ToastUtil.setToast(s);
                }
            }
        });
    }
}
