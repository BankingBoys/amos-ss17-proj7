package de.fau.amos.virtualledger.android.views.calendar;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * Created by Georg on 10.06.2017.
 */

public class CaldroidBankingFragment extends CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CaldroidBankingCellAdapter(getContext(), month, year, getCaldroidData(), getExtraData());
    }
}
