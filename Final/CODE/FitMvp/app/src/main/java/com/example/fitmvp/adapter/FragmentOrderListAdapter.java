package com.example.fitmvp.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/*
 * 控制页面切换（好友界面）
 */
public class FragmentOrderListAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private String[] titles;
    private Context context;
    public FragmentOrderListAdapter(FragmentManager mFragmentManager,
                                    List fragmentList, String[] title) {
        super(mFragmentManager);
        list = fragmentList;
        titles=title;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i < list.size()) {
            fragment = list.get(i);
        } else {
            fragment = list.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0)
            return titles[position];
        return null;
    }
}