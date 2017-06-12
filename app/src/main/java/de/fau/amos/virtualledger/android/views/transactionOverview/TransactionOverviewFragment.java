package de.fau.amos.virtualledger.android.views.transactionOverview;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.views.shared.totalAmount.TotalAmountFragment;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.ByActualMonth;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.CustomFilter;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.FilterByName;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.TransactionFilter;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

public class TransactionOverviewFragment extends Fragment implements java.util.Observer {
    private static final String TAG = TransactionOverviewFragment.class.getSimpleName();

    TransactionAdapter adapter;
    private View mainView;
    ArrayList<Transaction> allTransactions = new ArrayList<>();
    ArrayList<Transaction> presentedTransactions = new ArrayList<>();
    private TransactionFilter transactionFilter = new ByActualMonth();
    private ListView bookingListView;
    private HashMap<String, Boolean> mappingCheckBoxes = new HashMap<>();

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
        presentedTransactions.clear();
        boolean filter = hasItemsChecked(mappingCheckBoxes);
        for (BankAccountBookings bankAccountBookings : bankAccountBookingsList) {
            for (Booking booking : bankAccountBookings.getBookings()) {
                Transaction transaction = new Transaction(
                        bankAccessCredentialDB
                                .getAccountName(
                                        authenticationProvider.getEmail(),
                                        bankAccountBookings.getBankaccessid(),
                                        bankAccountBookings.getBankaccountid()),
                        booking);

                this.allTransactions.add(transaction);
                this.presentedTransactions.add(transaction);
            }
        }

        showUpdatedTransactions();
    }

    void showUpdatedTransactions() {
        this.adapter.clear();
        this.presentedTransactions.clear();
        this.presentedTransactions.addAll(this.allTransactions);
        for (Transaction actualTransaction : new LinkedList<>(this.presentedTransactions)) {
            if (this.transactionFilter.shouldBeRemoved(actualTransaction)) {
                this.presentedTransactions.remove(actualTransaction);
            }
        }
        logger().log(Level.INFO, "Number of presented transactions: " + presentedTransactions.size());
        for (Transaction actualTransaction : this.presentedTransactions) {
            this.adapter.add(actualTransaction);
        }
        this.adapter.sort(new TransactionsComparator());
        this.adapter.notifyDataSetChanged();//Fixme doenst work?
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        bookingListView = (ListView) this.mainView.findViewById(R.id.transaction_list);

        final Spinner spinner = (Spinner) mainView.findViewById(R.id.transactionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainView.getContext(),
                R.array.transactionfilter, R.layout.filter_spinner_item);
        adapter.setDropDownViewResource(R.layout.filter_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final TransactionOverviewFragment _this = this;

        /**
         * Color of the little spinner triangle.
         * Usually its an image and have to be completly redisigned and recompiled into
         * all android spinners.
         * When you do that and you change your master color, all android image icons have to be
         * recompiled (this is bad)
         * --> this is a fancy workaround
         */
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorBankingOverview), PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView selectedTextView = (TextView) spinner.getSelectedView();
                if (selectedTextView == null) {
                    logger().info("Nothing selected in spinner. Filtering for 12 months.");
                    _this.filterTransactions("Last 12 months", null);
                    return;
                }
                _this.filterTransactions(selectedTextView.getText().toString(), selectedTextView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setSelection(0);
        return this.mainView;
    }

    void filterTransactions(String by, final TextView selectedTextView) {
        logger().log(Level.INFO, "Selected filter: " + by);
        TransactionFilter transactionFilter = FilterByName.getTransactionFilterByUIName(by);
        final TransactionOverviewFragment _this = this;
        if (transactionFilter == null) {
            final SpecifyDateDialog chooserDialogContent = SpecifyDateDialog.newInstance();
            ContextThemeWrapper ctw = new ContextThemeWrapper(mainView.getContext(), R.style.AlternativeAltertDialogTheme);
            AlertDialog.Builder builder = new AlertDialog.Builder(ctw)
                    .setTitle("")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (selectedTextView != null) {
                                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                                        String selectionText = dateFormatter.format(chooserDialogContent.getStartCalendar().getTime())//
                                                + "-" + dateFormatter.format(chooserDialogContent.getEndCalendar().getTime());
                                        selectedTextView.setText(selectionText);
                                        _this.transactionFilter = new CustomFilter(chooserDialogContent.getStartCalendar().getTime(),
                                                chooserDialogContent.getEndCalendar().getTime());
                                        _this.showUpdatedTransactions();

                                    }

                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    );
            builder.setView(chooserDialogContent.onCreateView(getActivity().getLayoutInflater(), null, null));
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            return;
        }
        logger().log(Level.INFO, "Direct filter found for " + by);
        this.transactionFilter = transactionFilter;

        this.showUpdatedTransactions();
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
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

    public void setCheckedMap(HashMap<String, Boolean> map) {
        this.mappingCheckBoxes = map;
    }

    public static boolean hasItemsChecked(HashMap<String, Boolean> map) {
        Boolean ret = false;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext() && !ret) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ret = (Boolean) entry.getValue();
        }
        return ret;
    }
}

