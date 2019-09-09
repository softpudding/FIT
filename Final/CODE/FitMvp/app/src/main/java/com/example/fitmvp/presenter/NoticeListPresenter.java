package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.NoticeBean;
import com.example.fitmvp.contract.NoticeListContract;
import com.example.fitmvp.model.NoticeListModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.NoticeListActivity;

import java.util.HashMap;
import java.util.List;

public class NoticeListPresenter extends BasePresenter<NoticeListActivity> implements NoticeListContract.Presenter {
    private NoticeListModel model = new NoticeListModel();

    @Override
    public void getNotice() {
        model.getNotice(new NoticeListContract.Model.Callback() {
            @Override
            public void success(List<NoticeBean> list) {
                getIView().setNotice(list);
                getIView().showList();
            }

            @Override
            public void fail() {
                ToastUtil.setToast("获取公告失败");
            }
        });
    }
}
