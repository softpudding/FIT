package com.example.fitmvp.presenter;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendRecommendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.model.FriendRecommendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.view.activity.FriendRecommendActivity;

import java.util.HashMap;
import java.util.List;

public class FriendRecommendPresenter extends BasePresenter<FriendRecommendActivity>
        implements FriendRecommendContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new FriendRecommendModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("recommend", models[0]);
        return map;
    }

    @Override
    public FriendEntry getUser(String username){
        UserEntry user = BaseApplication.getUserEntry();
        return FriendEntry.getFriend(user,username,user.appKey);
    }

    @Override
    public void getRecommendList(){
        UserEntry user = BaseApplication.getUserEntry();
        List<FriendRecommendEntry> list = user.getRecommends();
        getIView().setRecommendList(list);
    }
}
