package com.example.fitmvp.contract;

import com.example.fitmvp.bean.NoticeBean;

import java.util.List;

public interface NoticeListContract {
    interface Model {
        void getNotice(Callback callback);
        interface Callback{
            void success(List<NoticeBean> list);
            void fail();
        }
    }

    interface View {
    }

    interface Presenter {
        void getNotice();
    }
}
