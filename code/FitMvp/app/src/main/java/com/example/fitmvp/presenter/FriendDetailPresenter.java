package com.example.fitmvp.presenter;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.contract.FriendDetailContract;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.model.MessageModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.FriendDetailActivity;
import com.nostra13.universalimageloader.utils.L;

import java.util.HashMap;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class FriendDetailPresenter extends BasePresenter<FriendDetailActivity>
        implements FriendDetailContract.Presenter {

    private FriendRecommendModel recommendModel = new FriendRecommendModel();
    private FriendModel friendModel = new FriendModel();
    private MessageModel msgModel = new MessageModel();

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
                            // 刷新验证消息列表
                            getIView().updateRecommend();
                            getIView().finish();
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
                    // TODO：刷新验证消息UI
                    getIView().finish();
                } else {
                    //拒绝好友请求失败
                    LogUtils.e("refuse_invite","fail :"+ responseMessage);
                }
            }
        });
    }

    @Override
    public void deleteFriend(final String username){
        JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i==0){
                    userInfo.removeFromFriendList(new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (0 == responseCode) {
                                //移出好友列表成功
                                // 刷新好友列表、聊天记录列表、验证信息列表
                                recommendModel.updateRecommend(username,"已删除");

                                friendModel.deleteFriend(username, new FriendContract.Model.InfoHint() {
                                    @Override
                                    public void updateFriend() {
                                        getIView().updateFriendList();
                                    }
                                });

                                Boolean flag = msgModel.deleteConv(username);
                                if(flag){
                                    getIView().updateMsgList();
                                }
                                getIView().finish();

                            } else {
                                //移出好友列表失败
                                LogUtils.e("delete_friend_fail",responseMessage);
                            }
                        }
                    });
                }
                else {
                    LogUtils.e("find_user",s);
                }
            }
        });
    }
}
