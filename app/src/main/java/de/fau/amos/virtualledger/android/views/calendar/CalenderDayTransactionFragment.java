package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSupplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.TransactionListFragment;

/**
 * Created by Simon on 18.06.2017.
 */

public class CalenderDayTransactionFragment extends Fragment {

    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";
    private TransactionListFragment transactionListFragment;
    private BankTransactionSupplier bankTransactionSupplier;

    private TextView amount;

    private View view;

    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        double amountBundle = bundle.getDouble(BUNDLE_PARAMETER_TOTALAMOUNT);
        if (bundle != null) {
            amount.setText(getFormatedDouble(amountBundle));
            changeAmountTextColor(amountBundle);
        }
    }

    /**
     *
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.calendar_view_daily_transactions, container, false);


        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        this.transactionListFragment = new TransactionListFragment();
        if (this.bankTransactionSupplier != null) {
            this.transactionListFragment.pushDataProvider(this.bankTransactionSupplier);
        }
        fragmentTransaction.replace(R.id.transaction_list_placeholder, transactionListFragment);
        fragmentTransaction.commit();


        amount = (TextView) view.findViewById(R.id.calendar_view_daily_transaction_amount);
        return this.view;
    }

    /**
     *
     */
    public static CalenderDayTransactionFragment newInstance(BankTransactionSupplier transactionSupplier, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putDouble(BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        CalenderDayTransactionFragment fragment = new CalenderDayTransactionFragment();
        fragment.setArguments(bundle);
        fragment.pushTransactionSupplier(transactionSupplier);
        return fragment;
    }

    private void pushTransactionSupplier(BankTransactionSupplier transactionSupplier) {
        this.bankTransactionSupplier = transactionSupplier;
        if (this.transactionListFragment != null) {
            this.transactionListFragment.pushDataProvider(this.bankTransactionSupplier);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.bankTransactionSupplier != null) {
            this.transactionListFragment.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.bankTransactionSupplier != null) {
            this.transactionListFragment.onResume();
        }
    }

    /**
     *
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    /**
     *
     */
    private String getFormatedDouble(double number) {
        return String.format(Locale.GERMAN, "%.2f", number);
    }

    /**
     *
     */
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
}
