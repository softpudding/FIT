package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.FriendAddContract;
import com.example.fitmvp.presenter.FriendAddPresenter;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FriendAddActivity extends BaseActivity<FriendAddPresenter> implements FriendAddContract.View {
    @Bind(R.id.send_reason)
    Button sendReason;
    @Bind(R.id.input_reason)
    EditText inputReason;

    private String targetUserPhone;
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
        actionbar.setTitle("验证信息");
    }

    protected FriendAddPresenter loadPresenter() {
        return new FriendAddPresenter();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        targetUserPhone = intent.getStringExtra("targetUser");
    }

    @Override
    protected void initListener() {
        sendReason.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SpUtils spUtils = new SpUtils();
        String myName = (String)spUtils.get("nickname","");
        inputReason.setText("我是"+myName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_friend;
    }

    @Override
    protected void otherViewClick(View view) {
        String searchingPhone = getInputReason();
        // 输入不为空时进行查找
        if(!TextUtils.isEmpty(searchingPhone)){
            mPresenter.addFriend(targetUserPhone,getInputReason());
        }
        else{
            ToastUtil.setToast("验证信息不能为空");
        }
    }

    private String getInputReason(){
        return inputReason.getText().toString().trim();
    }

    // 返回搜索界面
    public void goBack(){
        finish();
    }

}
