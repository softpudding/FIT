package com.example.fitmvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.friends.FragmentFrdList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        LogUtils.d("handleEvent",fromUsername);
        model.addRecommend(fromUsername,reason,type);
        switch (type) {
            //收到好友邀请
            case invite_received:
                LogUtils.e("handelEvent","invite_received");
                getIView().setCachedNewFriendNum();
                break;
            //对方接收了你的好友邀请
            case invite_accepted:
                friendModel.addFriend(fromUsername, new FriendContract.Model.InfoHint() {
                    @Override
                    public void updateFriend() {
                        new Handler().post(new Runnable() {
                            public void run() {
                                //在这里来写你需要刷新的地方
                                getIView().updateData();
                            }
                        });
                    }
                });
                break;

            //对方拒绝了你的好友邀请
            case invite_declined:
                getIView().setCachedNewFriendNum();
                break;

            //对方将你从好友中删除
            case contact_deleted:
                // TODO： 删除好友 未完成
                friendModel.deleteFriend(fromUsername);
                getIView().setCachedNewFriendNum();
                break;
            default:
                break;
        }
    }
    @Override
    public List<FriendEntry> getFriendList(){
        FriendModel friendModel = (FriendModel) getiModelMap().get("friend");
        List<FriendEntry> list = friendModel.getFriendList();
        Collections.sort(list, new Comparator<FriendEntry>() {
            // 按显示的名字升序排序
            @Override
            public int compare(FriendEntry f1, FriendEntry f2) {
                String name1,name2;
                name1 = f1.noteName;
                if(name1.equals("")){
                    name1 = f1.nickName;
                }
                if(name1.equals("")){
                    name1 = f1.username;
                }
                name2 = f2.noteName;
                if(name2.equals("")){
                    name2 = f2.nickName;
                }
                if(name2.equals("")){
                    name2 = f2.username;
                }
                Integer diff = name1.compareTo(name2);
                if (diff > 0) {
                    return 1;
                }
                else if (diff < 0) {
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }

    public void initCacheNum(){
        UserEntry user = BaseApplication.getUserEntry();
        List<FriendRecommendEntry> list = user.getRecommends();
        Integer cnt = 0;
        for(FriendRecommendEntry entry : list){
            if(entry.state.equals("请求加为好友")){
                cnt++;
            }
        }
        SpUtils.setCachedNewFriendNum(cnt);
        getIView().setCachedNewFriendNum();
    }
}
