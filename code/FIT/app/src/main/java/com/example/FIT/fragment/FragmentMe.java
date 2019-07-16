package com.example.FIT.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.FIT.R;
import com.example.FIT.activity.SettingActivity;


public class FragmentMe extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_me, container, false);


        return view;
    }
    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        ImageButton toSetting = getActivity().findViewById(R.id.setting_button);
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