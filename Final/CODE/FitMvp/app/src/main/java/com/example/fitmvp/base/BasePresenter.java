package com.example.fitmvp.base;

import com.example.fitmvp.mvp.IPresenter;
import com.example.fitmvp.mvp.IView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView> implements IPresenter {
    private WeakReference actReference;
    protected V iView;

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }
}
