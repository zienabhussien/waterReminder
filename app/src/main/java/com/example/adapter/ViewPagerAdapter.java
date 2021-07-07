package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter{
 ArrayList<Fragment> mFragmentList = new ArrayList<>();
 ArrayList<String> mFragmentPageTitle = new ArrayList<>();

    public ViewPagerAdapter( FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentPageTitle.size();
    }

    public void addFragments(Fragment fragment, String pageTitle){
        mFragmentList.add(fragment);
        mFragmentPageTitle.add(pageTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentPageTitle.get(position);
    }
}
