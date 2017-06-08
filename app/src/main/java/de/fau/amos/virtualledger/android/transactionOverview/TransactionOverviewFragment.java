package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.menu.MainMenu;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

public class TransactionOverviewFragment extends Fragment implements java.util.Observer {
    private static final String TAG = TransactionOverviewFragment.class.getSimpleName();

    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;

    private TextView sumView = null;
    private TransactionAdapter adapter;
    private View mainView;
    private ArrayList<Transaction> allTransactions = new ArrayList<>();
    ListView bookingListView;
    View separator;

    /**
     *
     */
    @Inject
    BankingProvider bankingProvider;
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankingDataManager bankingDataManager;
    private List<BankAccountBookings> bankAccountBookingsList;

    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        this.adapter = new TransactionAdapter(this.getActivity(), R.id.transaction_list, new ArrayList<Transaction>());
        bookingListView.setAdapter(adapter);
        /*refreshTotalAmount();*/
    }

    @Override
    public void onResume() {
        super.onResume();
        bankingDataManager.addObserver(this);

        switch (bankingDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                bankingDataManager.sync();
                break;
            case SYNCED:
                onBankingDataChanged();
                break;
        }
    }

    private void onBankingDataChanged() {
        try {
            bankAccountBookingsList = bankingDataManager.getBankAccountBookings();
            //TODO
            if ((bankAccountBookingsList == null || bankAccountBookingsList.size() == 0) && (getActivity() instanceof MainMenu)) {
                Fragment fragment = new NoBankingAccessesFragment();
                openFragment(fragment);
            }
            onBookingsUpdated();

        } catch (BankingSyncFailedException ex) {
            //TODO
            Log.e(TAG, "Error occured in Observable from bank overview");
            Toast.makeText(getActivity(), "Verbindungsprobleme mit dem Server, bitte versuchen Sie es erneut", Toast.LENGTH_LONG).show();
        }
    }

    private void onBookingsUpdated() {
        for (BankAccountBookings bankAccountBookings : bankAccountBookingsList) {
            for (Booking booking : bankAccountBookings.getBookings()) {
                Transaction transaction = new Transaction(
                        bankAccessCredentialDB
                                .getAccountName(
                                        authenticationProvider.getEmail(),
                                        bankAccountBookings.getBankaccessid(),
                                        bankAccountBookings.getBankaccountid()),
                        booking);

                allTransactions.add(transaction);
                Calendar calTransaction = Calendar.getInstance();
                calTransaction.setTime(transaction.booking().getDate());

                Calendar calToday = new GregorianCalendar();
                if (calTransaction.get(Calendar.MONTH) == calToday.get(Calendar.MONTH)) {
                    adapter.add(transaction);
                }

                refreshTotalAmount();
            }
        }
        adapter.sort(new TransactionsComparator());
    }


    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        bookingListView = (ListView) this.mainView.findViewById(R.id.transaction_list);
        separator = (View) mainView.findViewById(R.id.transactionSeparator);
        return this.mainView;
    }

    private void refreshTotalAmount() {
        double totalAmount = 0;
        for (int i = 0; i < this.allTransactions.size(); i++) {
            Transaction transaction = this.allTransactions.get(i);
            totalAmount += transaction.booking().getAmount();
        }
        this.sumView = (TextView) this.mainView.findViewById(R.id.transaction_sum_text);

        String bankBalanceString = String.format(Locale.GERMAN, "%.2f", totalAmount);
        if(totalAmount < 0)
        {
            int redColor = ContextCompat.getColor(this.getActivity(), R.color.colorNegativeAmount);
            sumView.setTextColor(redColor);
        } else if(totalAmount == 0)
        {
            int blueColor = ContextCompat.getColor(this.getActivity(), R.color.colorBankingOverview);
            sumView.setTextColor(blueColor);
        } else
        {
            int greenColor = ContextCompat.getColor(this.getActivity(), R.color.colorBankingOverviewLightGreen);
            sumView.setTextColor(greenColor);
        }

        sumView.setText("Total amount: " + bankBalanceString);
        separator.setVisibility(View.VISIBLE);
    }

    private void openFragment(Fragment fragment) {
        if (null != fragment) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    public void update(final Observable o, final Object arg) {
        onBankingDataChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        bankingDataManager.deleteObserver(this);
    }
}
