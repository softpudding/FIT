package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.friends.FragmentFrdList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendPresenter extends BasePresenter<FragmentFrdList> implements FriendContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new FriendModel(),
                new FriendRecommendModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("friend", models[0]);
        map.put("recommend",models[1]);
        return map;
    }

    @Override
    public void handleEvent(String fromUsername, String reason, ContactNotifyEvent.Type type){
        FriendRecommendModel model = (FriendRecommendModel) getiModelMap().get("recommend");
        FriendModel friendModel = (FriendModel) getiModelMap().get("friend");
        model.addRecommend(fromUsername,reason,type);
        switch (type) {
            //收到好友邀请
            case invite_received:
                break;
            //对方接收了你的好友邀请
            case invite_accepted:
                friendModel.addFriend(fromUsername);
                break;

            //对方拒绝了你的好友邀请
            case invite_declined:
                break;

            //对方将你从好友中删除
            case contact_deleted:
                friendModel.deleteFriend(fromUsername);
                break;
            default:
                break;
        }
    }
    @Override
    public List<FriendEntry> getFriendList(){
        FriendModel friendModel = (FriendModel) getiModelMap().get("friend");
        return friendModel.getFriendList();
    }
}
