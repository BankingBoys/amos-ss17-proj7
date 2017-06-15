package de.fau.amos.virtualledger.android.views.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
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

    private static final String BUNDLE_PARAMETER_TRANSACTIONLIST = "transactionlist";
    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";

    @Inject
    BankingDataManager bankingDataManager;

    private HashMap<DateTime, BankingDateInformation> bankingDateInformationMap;
    List<Transaction> transactionList;
    private double totalAmount;

    public static CaldroidBankingFragment newInstance(int month, int year, ArrayList<Transaction> transactionList, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CaldroidBankingFragment.BUNDLE_PARAMETER_TRANSACTIONLIST, transactionList);
        bundle.putDouble(CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        bundle.putInt(CaldroidFragment.MONTH, month);
        bundle.putInt(CaldroidFragment.YEAR, year);
        CaldroidBankingFragment fragment = new CaldroidBankingFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            transactionList = bundle.getParcelableArrayList(CaldroidBankingFragment.BUNDLE_PARAMETER_TRANSACTIONLIST);
            totalAmount = bundle.getDouble(CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT);
        } else {
            throw new InvalidParameterException("No data found in bundle! Please check if you instantiate CaldroidBankingFragment with " + CaldroidBankingFragment.BUNDLE_PARAMETER_TRANSACTIONLIST + "!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        init();

        return new CaldroidBankingCellAdapter(getContext(), month, year, getCaldroidData(), getExtraData(), bankingDateInformationMap );
    }

    private void init() {
        bankingDateInformationMap = new HashMap<>();
        Collections.sort(transactionList, new TransactionsComparator());

        for(int i = 0; i < transactionList.size(); ++i) {
            Booking booking = transactionList.get(i).booking();
            Date date = booking.getDate();
            DateTime exactDateTime = DateTime.forInstant(date.getTime(), TimeZone.getDefault());
            DateTime dateTime = new DateTime(exactDateTime.getYear(), exactDateTime.getMonth(), exactDateTime.getDay(), new Integer(0), new Integer(0), new Integer(0), new Integer(0));

            BankingDateInformation bankingDateInformation = bankingDateInformationMap.get(dateTime);
            if(bankingDateInformation == null) {
                bankingDateInformation = new BankingDateInformation(dateTime, totalAmount, new ArrayList<Booking>());
                bankingDateInformationMap.put(dateTime, bankingDateInformation);
            }

            bankingDateInformation.getBookingList().add(booking);
            totalAmount -= booking.getAmount();
        }
    }
}
