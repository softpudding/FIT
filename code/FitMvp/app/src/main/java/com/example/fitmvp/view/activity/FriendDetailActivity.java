package com.example.fitmvp.view.activity;

import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.presenter.FriendDetailPresenter;

import butterknife.ButterKnife;

public class FriendDetailActivity extends BaseActivity<FriendDetailPresenter> implements FriendContract.View {
    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("添加好友");
    }

    protected FriendDetailPresenter loadPresenter() {
        return new FriendDetailPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        // search.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_info;
    }

    @Override
    protected void otherViewClick(View view) {

    }
}
