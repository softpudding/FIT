package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.RecordContract;
import com.example.fitmvp.model.RecordModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.FragmentRecord;

import java.util.List;

public class RecordPresenter extends BasePresenter<FragmentRecord> implements RecordContract.Presenter {
    private RecordModel model = new RecordModel();

    @Override
    public void getAllRecords() {
        model.getAllRecords(new RecordContract.Model.Callback() {
            @Override
            public void success(List<RecordBean> list) {
                getIView().updateList(list);
            }

            @Override
            public void fail() {
                ToastUtil.setToast("获取记录失败");
            }
        });
    }
}
