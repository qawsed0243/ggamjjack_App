package com.ggam.jjac.voca.Frag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by song on 2017-05-27.
 */

public class TabFragAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    TabFragment1 tabFragment1;
    TabFragment2 tabFragment2;
    TabFragment3 tabFragment3;
    TabFragment4 tabFragment4;
    TabFragment5 tabFragment5;

    public TabFragAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        tabFragment1= new TabFragment1();
        tabFragment2= new TabFragment2();
        tabFragment3= new TabFragment3();
        tabFragment4= new TabFragment4();
        tabFragment5= new TabFragment5();
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                return tabFragment1;
            case 1:
                return tabFragment2;
            case 2:
                return tabFragment3;
            case 3:
                return tabFragment4;
            case 4:
                return tabFragment5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
