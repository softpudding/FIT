package com.example.fitmvp.presenter;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.contract.FriendDetailContract;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.FriendDetailActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

public class FriendDetailPresenter extends BasePresenter<FriendDetailActivity>
        implements FriendDetailContract.Presenter {
    private FriendRecommendModel recommendModel = new FriendRecommendModel();
    private FriendModel friendModel = new FriendModel();
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
       return null;
    }

    @Override
    public void acceptInvite(final String username){
        ContactManager.acceptInvitation(username, BaseApplication.getAppKey(), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    //接收好友请求成功
                    LogUtils.e("accept_invite","success");
                    recommendModel.updateRecommend(username,"已同意");
                    friendModel.addFriend(username, new FriendContract.Model.InfoHint() {
                        @Override
                        public void updateFriend() {
                            // refresh ui
                            getIView().updateFriendList();
                        }
                    });
                    getIView().finish();
                } else {
                    //接收好友请求失败
                    LogUtils.e("accept_invite","fail :"+ responseMessage);
                }
            }
        });
    }
    @Override
    public void refuseInvite(final String username){
        String reason = "对方拒绝了你的请求";
        ContactManager.declineInvitation(username, BaseApplication.getAppKey(), reason, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    //拒绝好友请求成功
                    LogUtils.e("refuse_invite","success");
                    recommendModel.updateRecommend(username,"已拒绝");
                    getIView().finish();
                } else {
                    //拒绝好友请求失败
                    LogUtils.e("refuse_invite","fail :"+ responseMessage);
                }
            }
        });
    }
}
