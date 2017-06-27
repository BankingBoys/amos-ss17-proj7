package de.fau.amos.virtualledger.android.views.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSuplierFilter;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.OneDayFilter;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

public class CaldroidBankingFragment extends CaldroidFragment {

    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";

    private HashMap<DateTime, BankingDateInformation> bankingDateInformationMap;
    private Supplier<Transaction> bankTransactionSupplier;
    private double totalAmount;

    public static CaldroidBankingFragment newInstance(int month, int year, Supplier<Transaction> bankTransactionSupplier, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putDouble(CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        bundle.putInt(CaldroidFragment.MONTH, month);
        bundle.putInt(CaldroidFragment.YEAR, year);
        CaldroidBankingFragment fragment = new CaldroidBankingFragment();
        fragment.setArguments(bundle);
        fragment.pushBankTransactionSupplier(bankTransactionSupplier);

        return fragment;
    }

    private void pushBankTransactionSupplier(Supplier<Transaction> bankTransactionSupplier) {
        this.bankTransactionSupplier = bankTransactionSupplier;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            totalAmount = bundle.getDouble(CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT);
        } else {
            throw new InvalidParameterException("No data found in bundle! Please check if you instantiate CaldroidBankingFragment with " + CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT + " !");
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

        return new CaldroidBankingCellAdapter(getContext(), month, year, getCaldroidData(), getExtraData(), bankingDateInformationMap);
    }

    private void init() {
        bankingDateInformationMap = new HashMap<>();

        List<Transaction> allTransactions = this.bankTransactionSupplier.getAll();

        for (Transaction transaction : allTransactions) {
            Booking booking = transaction.booking();
            Date date = booking.getDate();
            DateTime dateTime = getDateTime(date);

            if (!bankingDateInformationMap.containsKey(dateTime)) {
                BankingDateInformation bankingDateInformation = new BankingDateInformation(getTotalAmountFor(dateTime, totalAmount, allTransactions), //
                        new BankTransactionSuplierFilter(this.bankTransactionSupplier, //
                                new OneDayFilter(booking.getDate())));
                bankingDateInformationMap.put(dateTime, bankingDateInformation);
            }
        }
    }

    @NonNull
    private DateTime getDateTime(Date date) {
        DateTime exactDateTime = DateTime.forInstant(date.getTime(), TimeZone.getDefault());
        return new DateTime(exactDateTime.getYear(), exactDateTime.getMonth(), exactDateTime.getDay(), 0, 0, 0, 0);
    }

    private double getTotalAmountFor(DateTime date, double endTotalAmount, List<Transaction> allTransactions) {
        double dayAmount = endTotalAmount;
        long milliseconds = date.getMilliseconds(TimeZone.getDefault());
        for (Transaction t : allTransactions) {
            if (milliseconds < t.getCachedTimeAsLong())
                dayAmount -= t.booking().getAmount();
        }
        return dayAmount;
    }


}
