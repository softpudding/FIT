package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendAddContract;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.FriendAddActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

public class FriendAddPresenter extends BasePresenter<FriendAddActivity> implements FriendAddContract.Presenter {
    private FriendRecommendModel model = new FriendRecommendModel();
    @Override
    public void addFriend(final String targetUser, final String reason){
        ContactManager.sendInvitationRequest(targetUser, null, reason, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i == 0){
                    model.addRecommend(targetUser,reason,null);
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
