package de.fau.amos.virtualledger.android.views.calendar;

import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.views.transactionOverview.TransactionsComparator;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

/**
 * Created by Georg on 10.06.2017.
 */

public class CaldroidBankingFragment extends CaldroidFragment {

    @Inject
    BankingDataManager bankingDataManager;

    private HashMap<DateTime, BankingDateInformation> bankingDateInformationMap;

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        init();

        return new CaldroidBankingCellAdapter(getContext(), month, year, getCaldroidData(), getExtraData(), bankingDateInformationMap );
    }

    private void init() {
        bankingDateInformationMap = new HashMap<>();
        List<BankAccountBookings> bankAccountBookingsList = new ArrayList<>();

        try {
            bankAccountBookingsList = bankingDataManager.getBankAccountBookings();
        } catch (BankingSyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }

        for(int i = 0; i < bankAccountBookingsList.size(); ++i) {
            BankAccountBookings bankAccountBookings = bankAccountBookingsList.get(i);

            for(Booking booking : bankAccountBookings.getBookings()) {

            }
            double amount = 0.0;
            double amountDelta = 0.0;

            BankingDateInformation bankingDateInformation = new BankingDateInformation(DateTime.today(TimeZone.getDefault()), amount, new ArrayList<Booking>());
        }
    }
}
