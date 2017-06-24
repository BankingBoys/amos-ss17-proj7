package de.fau.amos.virtualledger.android.views.savings.add;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;


public class AddSavingsAccountPagerAdapter extends FragmentPagerAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountPagerAdapter.class.getSimpleName();

    private final List<AddSavingsAccountPage> pages;

    public AddSavingsAccountPagerAdapter(final FragmentManager fm, final List<AddSavingsAccountPage> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(final int position) {
        if(position < pages.size()) {
            return pages.get(position);
        } else {
            Log.e(TAG, "Trying to switch to invalid pager position");
            return null;
        }
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
