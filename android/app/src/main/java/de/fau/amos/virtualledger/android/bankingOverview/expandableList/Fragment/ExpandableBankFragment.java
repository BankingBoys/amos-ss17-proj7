package de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.BankAccessNameExtractor;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.DeleteBankAccessAction;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.LongClickDeleteListenerList;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 21.05.2017.
 */

public class ExpandableBankFragment extends Fragment {
    private static final String TAG = "BankAccessListFragment";

    /**
     *
     */
    ExpandableListView listView;

    TextView bankBalanceOverviewText;

    View separator;

    SparseArray<Group> groups = new SparseArray<Group>();

    List<BankAccess> bankAccessList;

    double bankBalanceOverview;

    /**
     *
     */
    @Inject
    BankingProvider bankingProvider;

    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        final ExpandableBankFragment __self = this;


        bankingProvider.getBankingOverview()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BankAccess>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<BankAccess> bankAccesses) {
                        bankAccessList = bankAccesses;
                        if (bankAccessList == null || bankAccesses.size() == 0) {
                            Fragment fragment = new NoBankingAccessesFragment();
                            openFragment(fragment);
                        }
                        createData();
                        ExpandableAdapterBanking adapter = new ExpandableAdapterBanking(getActivity(),
                                groups);
                        // TODO delete when refactored to inject in deleteAction
                        adapter.setBankingProvider(bankingProvider);

                        listView.setAdapter(adapter);
                        String bankBalanceString = String.format(Locale.GERMAN, "%.2f", bankBalanceOverview);
                        bankBalanceOverviewText.setText(bankBalanceString);
                        separator.setVisibility(View.VISIBLE);
                        final BankAccessNameExtractor getName = new BankAccessNameExtractor();
                        listView.setOnItemLongClickListener(
                                new LongClickDeleteListenerList(adapter, __self.getActivity(),
                                        bankAccessList,
                                        getName,
                                        new BiConsumer<BankAccess, BankAccount>() {
                                            @Override
                                            public void accept(BankAccess item1, BankAccount item2) {
                                                new DeleteBankAccessAction(__self.getActivity(), getName, bankingProvider).accept(item1, item2);
                                            }
                                        }
                                )

                        );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "Error occured in Observable from login.");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banking_overview_expandablelist_main_view, container, false);
        listView = (ExpandableListView) view.findViewById(R.id.expandableView);
        bankBalanceOverviewText = (TextView) view.findViewById(R.id.BankAccessBalanceOverview);
        separator = (View) view.findViewById(R.id.BankOverviewSeperator);
        return view;
    }

    /**
     *
     */
    private void createData() {
        int i = 0;
        sortAccesses();
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

    /**
     *
     */
    private void sortAccesses() {
        Collections.sort(bankAccessList, BankAccess.sortBankAccessByName);
    }

    /**
     *
     */
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
}
