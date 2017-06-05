package de.fau.amos.virtualledger.android.transactionOverview;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.BankAccessNameExtractor;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.DeleteBankAccessAction;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.LongClickDeleteListenerList;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment.ExpandableBankFragment;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Simon on 21.05.2017.
 */

public class TransactionOverviewFragment extends Fragment {
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
    @Inject
    AuthenticationProvider authenticationProvider;


    /**
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banking_overview_expandablelist_main_view, container, false);
        return view;
    }

}
