package com.example.ucmap.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.ucmap.fragment.AnalysisFragment;
import com.example.ucmap.fragment.LayerFragment;
import com.example.ucmap.fragment.SearchFragment;
import com.example.ucmap.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public MenuPageAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(new LayerFragment());
        fragments.add(new SearchFragment());
        fragments.add(new AnalysisFragment());
        fragments.add(new UserFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

}
