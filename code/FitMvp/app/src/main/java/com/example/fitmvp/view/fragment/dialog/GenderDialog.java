package com.example.fitmvp.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.fitmvp.utils.LogUtils;

public class GenderDialog extends DialogFragment {
    private Callback callback;

    public  interface Callback{
        void check(String choice);
        void save();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String [] genders = {"男", "女", "保密"};
        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
        builder.setTitle("性别");
        builder.setSingleChoiceItems(genders,
                -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int which) {
                        LogUtils.e("choose", String.valueOf(which));
                        callback.check(genders[which]);
                    }
                });

        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        callback.save();
                        dialogInterface.dismiss();
                    }
                });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        dialogInterface.dismiss();
                    }
                });
        return builder.create();
    }
}
