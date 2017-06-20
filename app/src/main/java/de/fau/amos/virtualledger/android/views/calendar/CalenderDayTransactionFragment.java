package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.android.views.shared.transactionList.TransactionAdapter;
import de.fau.amos.virtualledger.android.views.shared.transactionList.TransactionListFragment;

/**
 * Created by Simon on 18.06.2017.
 */

public class CalenderDayTransactionFragment extends Fragment {

    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";
    private static final String BUNDLE_PARAMETER_TRANSACTIONLIST = "transactionList";

    TransactionAdapter adapter;

    ListView bookingListView;

    TextView amount;

    View view;

    List<Transaction> transactionList= new ArrayList<>();

    TransactionListFragment transactionListFragment;

    private BankingDateInformation bankingDateInformation;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        double amountBundle = bundle.getDouble(BUNDLE_PARAMETER_TOTALAMOUNT);
        if(bundle!=null) {
            amount.setText(getFormatedDouble(amountBundle));
            changeAmountTextColor(amountBundle);
            this.transactionList = bundle.getParcelableArrayList(BUNDLE_PARAMETER_TRANSACTIONLIST);
        }
        adapter = new TransactionAdapter(getActivity(), R.id.transaction_list, new ArrayList<>(transactionList));
        bookingListView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.calendar_view_daily_transactions, container, false);
        bookingListView = (ListView) this.view.findViewById(R.id.calender_view_transaction_list);
        amount = (TextView) view.findViewById(R.id.calendar_view_daily_transaction_amount);
        return this.view;
    }

    public static CalenderDayTransactionFragment newInstance(List<Transaction> transactionList, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putDouble(BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        bundle.putParcelableArrayList(BUNDLE_PARAMETER_TRANSACTIONLIST, new ArrayList<Transaction>(transactionList));
        CalenderDayTransactionFragment fragment = new CalenderDayTransactionFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    public void setBankingDateInformation(BankingDateInformation bankingDateInformation) {
        this.bankingDateInformation = bankingDateInformation;
    }

 /*   @NonNull
    private BankTransactionSupplier getBankTransactionSupplier() {
        BankTransactionSupplier basicTransactionSupplier = new BankTransactionSupplierImplementation(this.getActivity(), bankAccountBookingsList);
        return basicTransactionSupplier;
    }*/

    private String getFormatedDouble(double number) {
        return String.format(Locale.GERMAN, "%.2f", number);
    }

    private void changeAmountTextColor(double amount_i) {
        if (amount_i < 0) {
            int redColor = ContextCompat.getColor(view.getContext(), R.color.colorNegativeAmount);
            amount.setTextColor(redColor);
        } else if (amount_i == 0) {
            int blueColor = ContextCompat.getColor(view.getContext(), R.color.colorBankingOverview);
            amount.setTextColor(blueColor);
        } else {
            int greenColor = ContextCompat.getColor(view.getContext(), R.color.colorBankingOverviewLightGreen);
            amount.setTextColor(greenColor);
        }
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }



}
