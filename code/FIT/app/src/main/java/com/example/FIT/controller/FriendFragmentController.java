package com.example.FIT.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.FIT.fragment.FragmentFrdList;
import com.example.FIT.fragment.FragmentMsg;

import java.util.ArrayList;

public class FriendFragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FriendFragmentController controller;

    public static FriendFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new FriendFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private FriendFragmentController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentMsg());
        fragments.add(new FragmentFrdList());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}