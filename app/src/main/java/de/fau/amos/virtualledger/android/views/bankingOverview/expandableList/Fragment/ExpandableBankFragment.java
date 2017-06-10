package de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.BankAccessNameExtractor;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.DeleteBankAccessAction;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.LongClickDeleteListenerList;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.views.shared.totalAmount.TotalAmountFragment;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class ExpandableBankFragment extends Fragment implements Observer {
    private static final String TAG = "BankAccessListFragment";
    @Inject
    BankingProvider bankingProvider;
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    BankingDataManager bankingDataManager;
    private ExpandableListView listView;
    private SparseArray<Group> groups = new SparseArray<>();
    private List<BankAccess> bankAccessList;
    private double bankBalanceOverview;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
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

    private void onBankAccessesUpdated() {
        createData();
        ExpandableAdapterBanking adapter = new ExpandableAdapterBanking(getActivity(),
                groups, bankingDataManager);

        listView.setAdapter(adapter);
        final BankAccessNameExtractor getName = new BankAccessNameExtractor();
        listView.setOnItemLongClickListener(
                new LongClickDeleteListenerList(adapter, getActivity(),
                        bankAccessList,
                        getName,
                        new BiConsumer<BankAccess, BankAccount>() {
                            @Override
                            public void accept(BankAccess item1, BankAccount item2) {
                                new DeleteBankAccessAction(bankingDataManager).accept(item1, item2);
                            }
                        }
                )

        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banking_overview_expandablelist_main_view, container, false);
        listView = (ExpandableListView) view.findViewById(R.id.expandableView);
        return view;
    }

    private void createData() {
        int i = 0;
        bankBalanceOverview = 0;
        sortAccesses();
        groups.clear();
        for (BankAccess access : bankAccessList) {
            Group group = new Group(access);
            List<BankAccount> accountList = sortAccounts(access.getBankaccounts());
            access.setBankaccounts(accountList);
            for (BankAccount account : access.getBankaccounts()) {
                group.children.add(account);
            }
            bankBalanceOverview += access.getBalance();
            groups.append(i, group);
            i++;
        }

    }

    private void sortAccesses() {
        Collections.sort(bankAccessList, BankAccess.sortBankAccessByName);
    }

    private List<BankAccount> sortAccounts(List<BankAccount> accounts) {
        Collections.sort(accounts, BankAccount.sortBankAccountByName);
        return accounts;
    }

    /**
     * opens a fragment through replacing another fragment
     */
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

    public void onBankingDataChanged() {
        try {
            bankAccessList = bankingDataManager.getBankAccesses();
            if ((bankAccessList == null || bankAccessList.size() == 0)) {
                Fragment fragment = new NoBankingAccessesFragment();
                openFragment(fragment);
            }
            onBankAccessesUpdated();

        } catch (BankingSyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
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
        TotalAmountFragment totalAmountFragment = new TotalAmountFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.banking_overview_total_amount_fragment_wrapper, totalAmountFragment, "banking_overview_total_amount_fragment");
        ft.commit();
    }
}
