package com.example.FIT.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FIT.R;
import com.example.FIT.item.RecordItem;

import java.util.ArrayList;
import java.util.List;


public class FragmentSocial extends Fragment {
    //例子，之后需要改为从后端获取
    private List<RecordItem> data;
    private void initData(){
        RecordItem item1 = new RecordItem();
        item1.setImage(R.drawable.item_friends);
        item1.setTitle("草莓");
        item1.setText("......");

        RecordItem item2 = new RecordItem();
        item2.setImage(R.drawable.item_home);
        item2.setTitle("饼干");
        item2.setText("......");

        data = new ArrayList<>();
        data.add(item1);
        data.add(item1);
        data.add(item2);
        data.add(item2);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_social, container, false);
    }
}
