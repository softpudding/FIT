package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.FriendSettingContract;
import com.example.fitmvp.model.FriendModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.FriendSettingActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class FriendSettingPresenter extends BasePresenter<FriendSettingActivity>
        implements FriendSettingContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new FriendModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("friend", models[0]);
        return map;
    }

    @Override
    public  void setNoteName(final String newName, final String userName){
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i==0){
                    // 修改备注名
                    userInfo.updateNoteName(newName, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if(i==0){
                                // 更新数据库
                                FriendModel model = (FriendModel)getiModelMap().get("friend");
                                if(model.updateNoteName(newName,userName)){
                                    // 修改成功
                                    getIView().updateFriendInfo();
                                }
                                else{
                                    ToastUtil.setToast("备注名修改失败，对方不在好友列表中");
                                }
                            }
                            else {
                                LogUtils.e("updateNoteName",s);
                            }
                        }
                    });
                }
                else {
                    LogUtils.e("updateNoteName",s);
                }
            }
        });
    }
}
