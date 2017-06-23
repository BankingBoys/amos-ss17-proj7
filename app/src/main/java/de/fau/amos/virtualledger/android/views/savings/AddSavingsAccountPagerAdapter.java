package de.fau.amos.virtualledger.android.views.savings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class AddSavingsAccountPagerAdapter extends FragmentPagerAdapter {

    public AddSavingsAccountPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        return new AddSavingsAccountNameFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
