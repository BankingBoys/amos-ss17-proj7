package de.fau.amos.virtualledger.android.views.calendar;

import android.os.Bundle;
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
import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSupplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.OneDayFilter;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

public class CaldroidBankingFragment extends CaldroidFragment {

    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";

    private HashMap<DateTime, BankingDateInformation> bankingDateInformationMap;
    private BankTransactionSupplier bankTransactionSupplier;
    private double totalAmount;

    public static CaldroidBankingFragment newInstance(int month, int year, BankTransactionSupplier bankTransactionSupplier, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putDouble(CaldroidBankingFragment.BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        bundle.putInt(CaldroidFragment.MONTH, month);
        bundle.putInt(CaldroidFragment.YEAR, year);
        CaldroidBankingFragment fragment = new CaldroidBankingFragment();
        fragment.setArguments(bundle);
        fragment.pushBankTransactionSupplier(bankTransactionSupplier);

        return fragment;
    }

    private void pushBankTransactionSupplier(BankTransactionSupplier bankTransactionSupplier) {
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

        List<Transaction> allTransactions = this.bankTransactionSupplier.getAllTransactions();
        




        for (Transaction transaction : allTransactions) {
            Booking booking = transaction.booking();
            Date date = booking.getDate();
            DateTime exactDateTime = DateTime.forInstant(date.getTime(), TimeZone.getDefault());
            DateTime dateTime = new DateTime(exactDateTime.getYear(), exactDateTime.getMonth(), exactDateTime.getDay(), 0, 0, 0, 0);

            if (!bankingDateInformationMap.containsKey(dateTime)) {
                BankingDateInformation bankingDateInformation = new BankingDateInformation(getTotalAmountFor(date, totalAmount, allTransactions), //
                        new BankTransactionSuplierFilter(this.bankTransactionSupplier, //
                                new OneDayFilter(booking.getDate())));
                bankingDateInformationMap.put(dateTime, bankingDateInformation);
            }

            BankingDateInformation bankingDateInformation = bankingDateInformationMap.get(dateTime);
        }
    }

    private double getTotalAmountFor(Date date, double endTotalAmount, List<Transaction> allTransactions) {
        double dayAmount = endTotalAmount;
        for (Transaction t : allTransactions) {
            if (t.booking().getDate().before(date) || t.booking().getDate().equals(date)) {
                dayAmount += t.booking().getAmount();
            }
        }
        return dayAmount;
    }


}
