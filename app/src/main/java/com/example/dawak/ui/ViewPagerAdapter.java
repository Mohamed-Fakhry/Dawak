package com.example.dawak.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Mohamed Fakhry on 13/02/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Inject
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
