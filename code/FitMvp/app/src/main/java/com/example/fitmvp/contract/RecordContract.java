package com.example.fitmvp.contract;

import com.example.fitmvp.bean.RecordBean;

import java.util.List;

public interface RecordContract {
    interface Model {
        void getAllRecords(final Callback callback);
        interface Callback{
            void success(List<RecordBean> list);
            void fail();
        }
    }

    interface View {
    }

    interface Presenter {
        void getAllRecords();
    }
}
