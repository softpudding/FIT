package com.example.fitmvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.contract.MeContract;
import com.example.fitmvp.presenter.MePresenter;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.LoginActivity;
import com.example.fitmvp.view.activity.SettingActivity;


public class FragmentMe extends BaseFragment<MePresenter> implements MeContract.View{
    private Button logout;
    private Button toSetting;
    private TextView textNickname;

   @Override
   protected Integer getLayoutId(){
       return R.layout.me;
   }

   @Override
   protected MePresenter loadPresenter() {
       return new MePresenter();
   }

   @Override
   protected void initData(){}

   @Override
   protected void initView(){
      logout = view.findViewById(R.id.button_logout);
      toSetting = view.findViewById(R.id.button_setting);
      textNickname = view.findViewById(R.id.text_nickname);
      textNickname.setText((String)SpUtils.get("nickname",""));
   }

   @Override
   protected void initListener(){
       logout.setOnClickListener(this);
       toSetting.setOnClickListener(this);
   }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_logout:
                mPresenter.logout();
                break;
            case R.id.button_setting:
                toSetting(view);
        }
    }

    private void toSetting(View view){
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void toLogin(){
        ToastUtil.setToast("成功退出当前账号");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}