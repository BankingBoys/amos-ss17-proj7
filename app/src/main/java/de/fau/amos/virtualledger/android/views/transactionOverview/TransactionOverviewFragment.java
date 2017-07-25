package de.fau.amos.virtualledger.android.views.transactionOverview;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.views.calendar.CalendarViewFragment;
import de.fau.amos.virtualledger.android.views.shared.totalAmount.TotalAmountFragment;
import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSuplierFilter;
import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSupplierImplementation;
import de.fau.amos.virtualledger.android.views.shared.transactionList.ItemCheckedMap;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.android.views.shared.transactionList.TransactionListFragment;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.ByActualMonth;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.CustomFilter;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Filter;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.FilterByName;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;

public class TransactionOverviewFragment extends Fragment implements java.util.Observer {
    private final static String fragmentTag = "TransactionOverviewFrag";
    private View mainView;
    private ItemCheckedMap itemCheckedMap = new ItemCheckedMap(new HashMap<String, Boolean>());

    TotalAmountFragment totalAmountFragment;

    private TransactionListFragment transactionListFragment;

    private boolean recentlyAddedAccessFlag;

    @BindView(R.id.transaction_overview_calendar_button)
    Button calendarButton;

    @BindView(R.id.transaction_overview_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.transaction_overview_linear_layout_content)
    LinearLayout linearLayoutContent;

    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankingDataManager bankingDataManager;

    private Filter filter = new ByActualMonth();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        ButterKnife.bind(this, mainView);

        final Spinner spinner = (Spinner) mainView.findViewById(R.id.transactionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainView.getContext(),
                R.array.transactionfilter, R.layout.filter_spinner_item);
        adapter.setDropDownViewResource(R.layout.filter_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final TransactionOverviewFragment _this = this;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView selectedTextView = (TextView) spinner.getSelectedView();
                if (selectedTextView == null) {
                    logger().info("Nothing selected in spinner. Filtering for 12 months.");
                    _this.filterTransactions("Last 12 months", selectedTextView, spinner);
                    return;
                }
                _this.filterTransactions(selectedTextView.getText().toString(), selectedTextView, spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setSelection(2);

        setHasOptionsMenu(true);
        return this.mainView;
    }

    void filterTransactions(String by, final TextView selectedTextView, final Spinner spinner) {
        logger().log(Level.INFO, "Selected filter: " + by);
        Filter transactionFilter = FilterByName.getTransactionFilterByUIName(by);
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
                                        _this.filter = new CustomFilter(//
                                                chooserDialogContent.getStartCalendar().getTime(),
                                                chooserDialogContent.getEndCalendar().getTime());
                                        _this.update(null,null);
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
        this.filter = transactionFilter;
        if(bankingDataManager.getSyncStatus() == SyncStatus.SYNCED) {
            this.transactionListFragment.pushDataProvider(getDateFilteredBankTransactionSupplier());
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }

    private void openFragment(Fragment fragment) {
        if (null != fragment) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_menu_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
        totalAmountFragment = new TotalAmountFragment();
        totalAmountFragment.setCheckedMap(itemCheckedMap);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.transaction_overview_total_amount_fragment_wrapper, totalAmountFragment, "transaction_overview_total_amount_fragment");
        ft.commit();


        this.transactionListFragment = new TransactionListFragment();

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.transaction_list_placeholder, transactionListFragment);
        ft.commit();
    }

    public void setCheckedMap(Map<String, Boolean> map) {
        this.itemCheckedMap.update(map);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_overview_app_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.transaction_overview_app_bar_refresh:
                setUiLoading(true);
                bankingDataManager.sync();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.transaction_overview_calendar_button)
    public void onOpenCalendar() {
        this.logger().info("Opening calendar fragment");
        CalendarViewFragment calendar = CalendarViewFragment.newInstance(
                getBankTransactionSupplier(),
                computeBalanceOfCheckedAccounts());
        openFragment(calendar);
    }

    private double computeBalanceOfCheckedAccounts() {
        List<BankAccess> bankAccessList = new ArrayList<>();
        double filteredBalance = 0.0;
        try {
            bankAccessList = bankingDataManager.getBankAccesses();
        } catch (SyncFailedException e) {
            return 0.0;
        }

        if (itemCheckedMap.hasItemsChecked()) {
            for (BankAccess bankAccess : bankAccessList) {
                for (BankAccount bankAccount : bankAccess.getBankaccounts()) {
                    if (this.itemCheckedMap.shouldBePresented(bankAccount.getBankid())) {
                        filteredBalance += bankAccount.getBalance();
                    }
                }
            }
        } else {
            for (BankAccess bankAccess : bankAccessList) {
                filteredBalance += bankAccess.getBalance();
            }
        }

        return filteredBalance;
    }

    @Override
    public void update(Observable observable, Object o) {
        this.logger().info("Updateing Transaction Overview Fragment");
        setUiLoading(false);
        totalAmountFragment.setCheckedMap(itemCheckedMap);
        this.transactionListFragment.pushDataProvider(getDateFilteredBankTransactionSupplier());

        checkForEmptyOrNullAccessList();
    }


    @NonNull
    private Supplier<Transaction> getDateFilteredBankTransactionSupplier() {
        Supplier<Transaction> filteredForSelection = getBankTransactionSupplier();
        return new BankTransactionSuplierFilter(filteredForSelection, this.filter);
    }

    @NonNull
    private Supplier<Transaction> getBankTransactionSupplier() {
        Supplier<Transaction> basicTransactionSupplier = new BankTransactionSupplierImplementation(this.getActivity(), getBankAccountBookings());
        return new BankTransactionSuplierFilter(basicTransactionSupplier, this.itemCheckedMap);
    }

    private List<BankAccountBookings> getBankAccountBookings() {
        try {
            return this.bankingDataManager.getBankAccountBookings();
        } catch (Exception e) {
            logger().log(Level.SEVERE, "Exception occured in get account bookings", e);
        }
        return new ArrayList<>();
    }

    @Override
    public void onResume() {
        this.logger().info("On Resume of Transaction View");
        totalAmountFragment.setCheckedMap(itemCheckedMap);
        this.bankingDataManager.addObserver(this);
        switch (bankingDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                setUiLoading(true);
                bankingDataManager.sync();
                break;
            case SYNC_IN_PROGRESS:
                setUiLoading(true);
                break;
            case SYNCED:
                setUiLoading(false);
                this.transactionListFragment.pushDataProvider(new BankTransactionSupplierImplementation(this.getActivity(), getBankAccountBookings()));
                checkForEmptyOrNullAccessList();
                break;
        }
        super.onResume();
    }

    private void setUiLoading(final boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        linearLayoutContent.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    private void checkForEmptyOrNullAccessList() {
        if(!recentlyAddedAccessFlag) {
            try {
                List<BankAccess> accessList = bankingDataManager.getBankAccesses();
                if(accessList == null || accessList.size() == 0) {
                    openFragment(new NoBankingAccessesFragment());
                }
            } catch(Exception e) {
                Log.e(fragmentTag, "could not fetch bank accounts");
            }
        } else {
            this.recentlyAddedAccessFlag = false;
        }
    }

    public void setRecentlyAddedAccessFlag(boolean flag) {
        this.recentlyAddedAccessFlag = flag;
    }

}

