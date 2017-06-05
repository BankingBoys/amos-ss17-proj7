package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.dtos.BankAccess;

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
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banking_overview_expandablelist_main_view, container, false);
        TextView sumView = (TextView) view.findViewById(R.id.transaction_sum_text);
        System.out.println(sumView.getText());
        sumView.setText("Ein Test");
        System.out.println(sumView.getText());
        return view;
    }

}
