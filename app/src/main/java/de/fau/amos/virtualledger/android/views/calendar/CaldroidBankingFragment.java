package de.fau.amos.virtualledger.android.views.calendar;

import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;
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

        // TODO get from Fragment instantiation
        List<Transaction> transactionList = new ArrayList<>();
        double totalAmount = 1000.000;
        
        Collections.sort(transactionList, new TransactionsComparator());

        for(int i = 0; i < transactionList.size(); ++i) {
            Booking booking = transactionList.get(i).booking();
            Date date = booking.getDate();
            DateTime dateTime = DateTime.forInstantNanos(date.getTime(), TimeZone.getDefault());

            BankingDateInformation bankingDateInformation = bankingDateInformationMap.get(dateTime);
            if(bankingDateInformation == null) {
                bankingDateInformation = new BankingDateInformation(dateTime, totalAmount, new ArrayList<Booking>());
                bankingDateInformationMap.put(dateTime, bankingDateInformation);
            }

            bankingDateInformation.getBookingList().add(booking);
            bankingDateInformation.setAmount(totalAmount - booking.getAmount());
            totalAmount -= booking.getAmount();
        }
    }
}
