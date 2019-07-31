package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.NoticeBean;
import com.example.fitmvp.contract.NoticeListContract;
import com.example.fitmvp.model.NoticeListModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.NoticeListActivity;

import java.util.HashMap;
import java.util.List;

public class NoticeListPresenter extends BasePresenter<NoticeListActivity> implements NoticeListContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new NoticeListModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("getNotice", models[0]);
        return map;
    }

    @Override
    public void getNotice() {
        LogUtils.e("onstart","getnotice");
        NoticeListModel model = (NoticeListModel) getiModelMap().get("getNotice");
        model.getNotice(new NoticeListContract.Model.Callback() {
            @Override
            public void success(List<NoticeBean> list) {
                getIView().setNotice(list);
                getIView().showList();
            }

            @Override
            public void fail() {

            }
        });
    }
}
