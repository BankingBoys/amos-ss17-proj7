package de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.views.bankingOverview.addBankAccess.AddBankAccessActivity;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.BankAccessNameExtractor;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.DeleteBankAccessAction;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.DeleteDialog;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.BankingOverviewHandler;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import de.fau.amos.virtualledger.android.views.shared.totalAmount.TotalAmountFragment;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class ExpandableBankFragment extends Fragment implements Observer {
    @SuppressWarnings("unused")
    private static final String TAG = "BankAccessListFragment";

    @Inject
    BankingProvider bankingProvider;
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    BankingDataManager bankingDataManager;

    @BindView(R.id.expandableView)
    ExpandableListView listView;

    private ExpandableAdapterBanking adapter;
    private final SparseArray<Group> groups = new SparseArray<>();
    private List<BankAccess> bankAccessList;
    private HashMap<String, Boolean> mappingCheckBoxes = new HashMap<>();

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // This line needs to stay right here!!! Otherwise bankingDataManager is null when passed to adapter
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        adapter = new ExpandableAdapterBanking(getActivity(),
                groups, bankingDataManager, mappingCheckBoxes);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.banking_overview_expandablelist_main_view, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        // add total amount fragment programmatically (bad practice in xml -> empty LinearLayout as wrapper)
        final FragmentManager fm = getFragmentManager();
        final TotalAmountFragment totalAmountFragment = new TotalAmountFragment();
        final FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.banking_overview_total_amount_fragment_wrapper, totalAmountFragment, "banking_overview_total_amount_fragment");
        ft.commit();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.banking_overview_app_bar, menu);
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

    @Override
    public void onPause() {
        super.onPause();
        bankingDataManager.deleteObserver(this);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.banking_overview_app_bar_add_bank_access:
                final Intent addBankAccessIntent = new Intent(getActivity(), AddBankAccessActivity.class);
                startActivity(addBankAccessIntent);
                return true;
            case R.id.banking_overview_app_bar_refresh:
                bankingDataManager.sync();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.banking_overview_finishButton)
    void onClickShowAllTransactions() {
        if (BankingOverviewHandler.hasItemsChecked(mappingCheckBoxes)) {
            ((MainMenu) getActivity()).switchToTransactionOverview(mappingCheckBoxes);
        }
    }

    @OnClick(R.id.banking_overview_enable_all_accounts_checkbox)
    void onClickEnableAllCheckbox(final CheckBox view) {
        final BankingOverviewHandler bankingOverview = BankingOverviewHandler.getInstance();
        mappingCheckBoxes = bankingOverview.setAllAccountsCheckedOrUnchecked(mappingCheckBoxes, view.isChecked());
        adapter.setMappingCheckBoxes(mappingCheckBoxes);
        listView.setAdapter(adapter);
    }

    @OnItemLongClick(R.id.expandableView)
    boolean onLongClickListViewItem(final View view, final int position) {
        final Group group = (Group) listView.getAdapter().getItem(position);
        final int index = adapter.getIndexForGroup(group);

        final DeleteDialog deleteDialog = new DeleteDialog(view.getContext(),
                bankAccessList.get(index), null, new BankAccessNameExtractor(),
                new BiConsumer<BankAccess, BankAccount>() {
                    @Override
                    public void accept(final BankAccess item1, final BankAccount item2) {
                        new DeleteBankAccessAction(bankingDataManager).accept(item1, item2);
                        bankingDataManager.sync();
                    }
                });
        deleteDialog.show();
        return true;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        onBankingDataChanged();
    }

    private void onBankingDataChanged() {
        try {
            bankAccessList = bankingDataManager.getBankAccesses();
            if ((bankAccessList == null || bankAccessList.size() == 0)) {
                final Fragment fragment = new NoBankingAccessesFragment();
                openFragment(fragment);
            }
            onBankAccessesUpdated();

        } catch (final SyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * opens a fragment through replacing another fragment
     */
    private void openFragment(final Fragment fragment) {
        if (null != fragment) {
            final FragmentManager manager = getFragmentManager();
            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_menu_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void onBankAccessesUpdated() {
        createData();
        adapter.setMappingCheckBoxes(mappingCheckBoxes);
        listView.setAdapter(adapter);
    }

    private void createData() {
        int i = 0;
        final BankingOverviewHandler bankingOverview = BankingOverviewHandler.getInstance();
        bankAccessList = bankingOverview.sortAccesses(bankAccessList);
        groups.clear();
        mappingCheckBoxes.clear();
        for (final BankAccess access : bankAccessList) {
            final Group group = new Group(access);
            final List<BankAccount> accountList = bankingOverview.sortAccounts(access.getBankaccounts());
            access.setBankaccounts(accountList);
            for (final BankAccount account : access.getBankaccounts()) {
                group.children.add(account);
                mappingCheckBoxes.put(account.getBankid(), false);
            }
            groups.append(i, group);
            i++;
        }
    }

}
