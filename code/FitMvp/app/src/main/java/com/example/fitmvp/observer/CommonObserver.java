package com.example.fitmvp.observer;

import android.content.Intent;

import com.example.fitmvp.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class CommonObserver<T> implements Observer<T> {
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onComplete() {
        System.out.println("onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        System.err.println("onError");
        System.err.println(e.getMessage());
    }

    protected abstract void onError(ApiException e);
}
