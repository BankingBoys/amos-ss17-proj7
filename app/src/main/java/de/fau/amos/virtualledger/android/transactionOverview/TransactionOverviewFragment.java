package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.dtos.Booking;

public class TransactionOverviewFragment extends Fragment {
    /**
     *
     */
    private TextView sumView = null;
    private double totalAmount = 0;

    /**
     *
     */
    @Inject
    BankingProvider bankingProvider;
    @Inject
    AuthenticationProvider authenticationProvider;


    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        refreshTotalAmount(view);

        ListView bookingListView = (ListView) view.findViewById(R.id.transaction_list);
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Booking booking = new Booking();
        booking.setAmount(-1);
        booking.setDate(new Date());

        Booking booking2 = new Booking();
        booking2.setAmount(1);
        booking2.setDate(new Date());

        transactions.add(new Transaction("Testbank", booking));
        transactions.add(new Transaction("Noch eine", booking2));
        bookingListView.setAdapter(new TransactionAdapter(this.getActivity(), R.id.transaction_list, transactions));

        return view;
    }

    private void refreshTotalAmount(View view) {
        this.sumView = (TextView) view.findViewById(R.id.transaction_sum_text);
        sumView.setText("Total amount: " + totalAmount);
    }

}
