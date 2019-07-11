package com.example.fitmvp.observer;

import android.content.Intent;

import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.exception.ExceptionEngine;

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
        System.err.println("onError: "+e.getMessage());
        onError(ExceptionEngine.handleException(e));
    }

    protected abstract void onError(ApiException e);
}
