package com.example.fitmvp.view.activity;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.presenter.SettingPresenter;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.view.fragment.dialog.GenderDialog;
import com.example.fitmvp.view.fragment.dialog.MyDatePickerDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    @Bind(R.id.info_phone)
    TextView infoPhone;
    @Bind(R.id.info_nickname)
    EditText infoNickname;
    @Bind(R.id.info_birthday)
    EditText infoBirthday;
    @Bind(R.id.info_gender)
    EditText infoGender;
    @Bind(R.id.info_height)
    EditText infoHeight;
    @Bind(R.id.info_weight)
    EditText infoWeight;
    @Bind(R.id.button_update_info)
    Button update;

    private String phone;
    private String oldNickname;
    private String oldBirthday;
    private String oldGender;
    private String oldHeight;
    private String oldWeight;

    private String newNickname;
    private String newBirthday;
    private String newGender;
    private String newHeight;
    private String newWeight;

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
        actionbar.setTitle("个人信息设置");
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        infoBirthday.setShowSoftInputOnFocus(false);
        infoBirthday.setKeyListener(null);
        infoGender.setShowSoftInputOnFocus(false);
        infoGender.setKeyListener(null);
    }

    @Override
    protected SettingPresenter loadPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void initData() {
        phone = (String)SpUtils.get("phone","");
        oldNickname = (String)SpUtils.get("nickname","");
        oldBirthday = (String)SpUtils.get("birthday","");
        oldGender = (String)SpUtils.get("gender","");
        oldHeight = (String)SpUtils.get("height","");
        oldWeight = (String)SpUtils.get("weight","");

        // 显示手机号
        infoPhone.setText(phone);
        // 显示昵称
        infoNickname.setText(oldNickname);
        // 显示生日
        infoBirthday.setText(oldBirthday);
        // 显示性别
        infoGender.setText(oldGender);
        // 显示身高
        infoHeight.setText(oldHeight);
        // 显示体重
        infoWeight.setText(oldWeight);
    }

    @Override
    protected void initListener() {
        update.setOnClickListener(this);
        infoBirthday.setOnClickListener(this);
        infoGender.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_update_info:
                otherViewClick(view);
                break;
            case R.id.info_birthday:
                showDatePicker(view);
                break;
            case R.id.info_gender:
                showGenderChoice(view);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        // TODO：保存更新
    }

    // 弹出日期选择对话框
    private void showDatePicker(View view){
        hideKeyboard(view);
        MyDatePickerDialog newFragment = new MyDatePickerDialog();  //实例
        newFragment.show(getSupportFragmentManager(),"datePicker"); // 显示出来
        newFragment.setCallback(new MyDatePickerDialog.Callback(){
            @Override
            public void save(String birthday) {
                newBirthday = birthday;
                infoBirthday.setText(newBirthday);
            }
        });
    }

    // 弹出性别选择框
    private void showGenderChoice(View veiw){
        hideKeyboard(view);
        GenderDialog dialog = new GenderDialog();
        dialog.show(getSupportFragmentManager(),"checkGender");
        dialog.setCallback(new GenderDialog.Callback() {
            @Override
            public void check(String choice) {
                newGender = choice;
            }

            @Override
            public void save() {
                infoGender.setText(newGender);
            }
        });
    }

    // 收起软键盘
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
