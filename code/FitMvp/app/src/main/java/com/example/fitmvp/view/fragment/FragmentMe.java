package com.example.fitmvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitmvp.R;
import com.example.fitmvp.view.activity.SettingActivity;

public class FragmentMe extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.me, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        Button toSetting = getActivity().findViewById(R.id.button_setting);
        toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                // intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    /*
    public void toSetting(View view){
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        // intent.setClass(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }*/

}