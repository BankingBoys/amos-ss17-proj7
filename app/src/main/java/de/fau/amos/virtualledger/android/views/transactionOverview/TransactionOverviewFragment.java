package de.fau.amos.virtualledger.android.views.transactionOverview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.views.shared.totalAmount.TotalAmountFragment;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

public class TransactionOverviewFragment extends Fragment implements java.util.Observer {
    private static final String TAG = TransactionOverviewFragment.class.getSimpleName();

    private TransactionAdapter adapter;
    private View mainView;
    private ArrayList<Transaction> allTransactions = new ArrayList<>();
    private ListView bookingListView;


    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankingDataManager bankingDataManager;

    private List<BankAccountBookings> bankAccountBookingsList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        adapter = new TransactionAdapter(getActivity(), R.id.transaction_list, new ArrayList<Transaction>());
        bookingListView.setAdapter(adapter);
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
            if ((bankAccountBookingsList == null || bankAccountBookingsList.size() == 0)) {
                Fragment fragment = new NoBankingAccessesFragment();
                openFragment(fragment);
            }
            onBookingsUpdated();

        } catch (BankingSyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }
    }

    private void onBookingsUpdated() {
        allTransactions.clear();
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
            }
        }
        adapter.sort(new TransactionsComparator());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        bookingListView = (ListView) this.mainView.findViewById(R.id.transaction_list);

        Spinner spinner = (Spinner) mainView.findViewById(R.id.transactionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainView.getContext(),
                R.array.transactionfilter, R.layout.filter_spinner_item);
        adapter.setDropDownViewResource(R.layout.filter_spinner_dropdown_item );
        spinner.setAdapter(adapter);
        /**
         * Color of the little spinner triangle.
         * Usually its an image and have to be completly redisigned and recompiled into
         * all android spinners.
         * When you do that and you change your master color, all android image icons have to be
         * recompiled (this is bad)
         * --> this is a fancy workaround
         */
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorBankingOverview), PorterDuff.Mode.SRC_ATOP);
        return this.mainView;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // add total amount fragment programmatically (bad practice in xml -> empty LinearLayout as wrapper)
        FragmentManager fm = getFragmentManager();
        TotalAmountFragment totalAmountFragment = new TotalAmountFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.transaction_overview_total_amount_fragment_wrapper, totalAmountFragment, "transaction_overview_total_amount_fragment");
        ft.commit();
    }
}

