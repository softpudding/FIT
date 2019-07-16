package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.WelcomeContract;
import com.example.fitmvp.presenter.WelcomePresenter;

import java.lang.ref.WeakReference;
import java.util.Timer;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View{
//    //声明一个静态的Handler内部类，并持有外部类的弱引用 （避免内存泄漏）
//    private static class InnerHandler extends Handler{
//        private final WeakReference<WelcomeActivity> mActivity;
//        public InnerHandler(WelcomeActivity activity){
//            mActivity = new WeakReference<WelcomeActivity>(activity);
//        }
//        @Override
//        public void handleMessage(Message msg){
//            WelcomeActivity activity = mActivity.get();
//            if(activity != null){
//               if(msg.what == 1){
//                   activity.jump.setText("跳过  "+ activity.second.toString());
//                   activity.second--;
//                   if (activity.second<0){
//                       //关闭定时器
//                       activity.timer.cancel();
//                       //跳往主界面
//                       activity.mPresenter.jump();
//                   }
//               }
//            }
//        }
//    }
//    private final InnerHandler mHandler = new InnerHandler(this);

    @InjectView(R.id.jump_timer)
    Button jump;

    private Integer second;
    private CountDownTimer timer;

    @Override
    protected void setBar(){
    }
    @Override
    protected WelcomePresenter loadPresenter() {
        return new WelcomePresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        jump.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        second = 3;
        timer = new CountDownTimer(second*1000,1000) {
            @Override
            public void onTick(long l) {
                if(second>0 && jump != null){
                    jump.setText("跳过 "+second);
                    second--;
                }
            }
            @Override
            public void onFinish() {
                mPresenter.jump();
            }
        };
        timer.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_page;
    }
    @Override
    protected void otherViewClick(View view) {
        timer.cancel();
        mPresenter.jump();
    }

    @Override
    public void toLogin(){
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void toMainPage(){
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        // 避免内存泄漏
        if(timer != null){
            timer.cancel();
        }
    }

}
