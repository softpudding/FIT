package com.example.fitmvp.contract;

import com.example.fitmvp.bean.RecordBean;

import java.util.List;

public interface MainPageContract {
    interface Model {
        void getCalValue(final calCallback callback);
        void getList(final listCallback callback);
        interface calCallback{
            void success(double target, double current);
            void fail();
        }
        interface listCallback{
            void success(List<RecordBean> list);
            void fail();
        }
    }

    interface View {
    }

    interface Presenter {
        void getCalValue();
        void getList();
    }
}
