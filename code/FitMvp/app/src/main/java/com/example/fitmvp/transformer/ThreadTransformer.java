package com.example.fitmvp.transformer;

import com.example.fitmvp.base.BaseHttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// 切换线程
public class ThreadTransformer<T> implements ObservableTransformer<T, T> {

    @Override
    public Observable<T> apply(Observable<T> tansFormerObservable) {
        return tansFormerObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

