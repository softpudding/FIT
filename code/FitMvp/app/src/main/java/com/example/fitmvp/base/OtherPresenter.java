package com.example.fitmvp.base;

import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.mvp.IPresenter;
import com.example.fitmvp.mvp.IView;

import java.lang.ref.WeakReference;

public abstract class OtherPresenter<M extends IModel, V extends IView> implements IPresenter {
    private WeakReference actReference;
    protected V iView;
    protected M iModel;

    public M getiModel() {
        iModel = loadModel(); //使用前先进行初始化
        return iModel;
    }

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

    public abstract M loadModel();
}
