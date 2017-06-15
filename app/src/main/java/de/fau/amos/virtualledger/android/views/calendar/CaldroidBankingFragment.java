package de.fau.amos.virtualledger.android.views.calendar;

import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import hirondelle.date4j.DateTime;

/**
 * Created by Georg on 10.06.2017.
 */

public class CaldroidBankingFragment extends CaldroidFragment {

    @Inject
    BankingDataManager bankingDataManager;

    private List<BankAccountBookings> bankAccountBookingsList;

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        init();

        HashMap<DateTime, BankingDateInformation> bankingDateInformationMap = new HashMap<>();

        return new CaldroidBankingCellAdapter(getContext(), month, year, getCaldroidData(), getExtraData(), bankingDateInformationMap );
    }

    private void init() {
        try {
            bankAccountBookingsList = bankingDataManager.getBankAccountBookings();
            if ((bankAccountBookingsList == null || bankAccountBookingsList.size() == 0)) {
                //do nothing
            }
        } catch (BankingSyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }
    }
}
