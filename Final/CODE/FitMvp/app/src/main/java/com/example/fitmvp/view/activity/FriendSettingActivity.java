package com.example.fitmvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.FriendSettingContract;
import com.example.fitmvp.presenter.FriendSettingPresenter;
import com.example.fitmvp.utils.ToastUtil;


import butterknife.Bind;
import butterknife.ButterKnife;


public class FriendSettingActivity extends BaseActivity<FriendSettingPresenter>
            implements FriendSettingContract.View {

    @Bind(R.id.noteName)
    EditText editNoteName;
    @Bind(R.id.text_warning)
    TextView warning;
    @Bind(R.id.button_editNoteName)
    Button edit;

    String newNoteName;
    String oldNoteName;
    String userName;

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
        actionbar.setTitle("修改备注");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        warning.setVisibility(View.INVISIBLE);
    }

    @Override
    protected FriendSettingPresenter loadPresenter() {
        return new FriendSettingPresenter();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        oldNoteName = intent.getStringExtra("noteName");
        editNoteName.setText(oldNoteName);
        userName = intent.getStringExtra("userName");
    }

    @Override
    protected void initListener() {
        edit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_set;
    }

    @Override
    protected void otherViewClick(View view) {
        newNoteName = (String)editNoteName.getText().toString().trim();
        if(newNoteName.length()>15){
            warning.setVisibility(View.VISIBLE);
        }
        else if(newNoteName.length()==0){
            warning.setVisibility(View.INVISIBLE);
            ToastUtil.setToast("备注名不能为空");
        }
        else{
            mPresenter.setNoteName(newNoteName,userName);
        }
    }

    //发送刷新数据的广播
    public void updateFriendInfo(){
        Intent friendInfoIntent = new Intent("updateFriendNoteName");
        friendInfoIntent.putExtra("newNoteName",newNoteName);
        LocalBroadcastManager.getInstance(FriendSettingActivity.this).sendBroadcast(friendInfoIntent);
        this.finish();
    }
}
