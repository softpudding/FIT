package com.example.fitmvp.chat.utils.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.example.fitmvp.chat.utils.keyboard.data.PageEntity;

public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
