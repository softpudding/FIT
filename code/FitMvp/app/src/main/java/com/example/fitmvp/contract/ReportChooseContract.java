package com.example.fitmvp.contract;

import com.example.fitmvp.bean.FormBean;

public interface ReportChooseContract {
    interface Model {
        void getReport(String start, String end, Callback callback);
        interface Callback{
            void success(FormBean formBean);
            void fail();
        }
    }

    interface View {
    }

    interface Presenter {
        void initDate();
        void getReport(String start, String end);
    }
}
