package de.fau.amos.virtualledger.android.views.savings.add;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class AddSavingsAccountPagerAdapter extends FragmentPagerAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountPagerAdapter.class.getSimpleName();

    public AddSavingsAccountPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return new AddSavingsAccountNameFragment();
            case 1:
                return new AddSavingsAccountAmountFragment();
            default:
                Log.e(TAG, "Trying to switch to invalid pager position");
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
