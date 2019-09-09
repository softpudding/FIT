package com.example.fitmvp.view.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
    private Callback callback;

    public  interface Callback{
        void save(String birthday);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();   //调用getInstance
        int year = c.get(Calendar.YEAR);    // 得到年
        int month = c.get(Calendar.MONTH);   //月
        int day = c.get(Calendar.DAY_OF_MONTH);   //日
        return new android.app.DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Integer mMonth;
        String yy = String.valueOf(year);
        String mm;
        String dd;
        if (month < 9) {
            mMonth = month + 1;
            mm = "0" + mMonth;
        } else {
            mMonth = month + 1;
            mm = String.valueOf(mMonth);
        }
        if(day <= 9){
            dd = "0" + day;
        }
        else{
            dd = String.valueOf(day);
        }
        String date = yy + "-" + mm + "-" + dd;
        callback.save(date);
    }
}
