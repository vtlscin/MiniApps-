package com.cursoandroid.tablayout.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cursoandroid.tablayout.fragments.FragmentA;
import com.cursoandroid.tablayout.fragments.FragmentB;

public class FragmentAdpater extends FragmentPagerAdapter {

    private String[] mtabTitles;

    public FragmentAdpater(FragmentManager fm, String[] mtabTitles) {
        super(fm);
        this.mtabTitles = mtabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FragmentA();
            case 1:
                return new FragmentB();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mtabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.mtabTitles[position];
    }
}
