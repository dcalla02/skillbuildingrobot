package com.dreslab.www.drethan_newapp.Helperfiles;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Michael on 7/25/2016.
 */
public class MyFragmentAdaptor extends FragmentPagerAdapter {
    List<Fragment> Listfragments;

    public MyFragmentAdaptor(FragmentManager fm, List<Fragment> Listfragments) {
        super(fm);
        this.Listfragments = Listfragments;
    }

    @Override
    public Fragment getItem(int position) {
        return Listfragments.get(position);
    }

    @Override
    public int getCount() {
        return Listfragments.size();
    }
}
