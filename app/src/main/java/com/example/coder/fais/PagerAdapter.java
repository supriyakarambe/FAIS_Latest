package com.example.coder.fais;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Supriya on 11/22/17.
 */

class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TreatmentInfoFragment tab1 = new TreatmentInfoFragment();
                return tab1;
            case 1:
                EmergencyContactFragment tab2 = new EmergencyContactFragment();
                return tab2;
            case 2:
                VideosFragment tab3 = new VideosFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
