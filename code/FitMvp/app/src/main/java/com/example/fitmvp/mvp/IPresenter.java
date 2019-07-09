package com.example.fitmvp.mvp;

public interface IPresenter<V extends IView> {

    // view 绑定
    void attachView(V view);

    // 防止内存泄漏,清楚presenter与activity之间的绑定
    void detachView();

    // 获取View
    IView getIView();
}
