package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendDetailContract;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.view.activity.FriendDetailActivity;

import java.util.HashMap;

public class FriendDetailPresenter extends BasePresenter<FriendDetailActivity> implements FriendDetailContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
       return null;
    }
}
