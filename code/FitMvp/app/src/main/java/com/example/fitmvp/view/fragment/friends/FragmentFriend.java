package com.example.fitmvp.view.fragment.friends;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitmvp.R;

public class FragmentFriend extends Fragment {

    private RadioGroup rg_tab;
    private FriendFragmentController controller;

    public FragmentFriend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);

        controller = FriendFragmentController.getInstance(this, R.id.id_fragment);
        controller.showFragment(0);
        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_msg:
                        controller.showFragment(0);
                        break;
                    case R.id.radioButton_frdlist:
                        controller.showFragment(1);
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }
}
