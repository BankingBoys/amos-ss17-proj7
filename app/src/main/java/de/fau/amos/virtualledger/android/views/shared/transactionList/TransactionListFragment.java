package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;

public class TransactionListFragment extends Fragment implements java.util.Observer, DataListening {
    TransactionAdapter adapter;
    private View mainView;

    private ListView bookingListView;

    private BankTransactionSupplier bankTransactionSupplier;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        adapter = new TransactionAdapter(getActivity(), R.id.transaction_list, new ArrayList<Transaction>());
        bookingListView.setAdapter(adapter);
    }

    public void pushDataProvider(BankTransactionSupplier supplier) {
        this.bankTransactionSupplier = supplier;
        this.bankTransactionSupplier.addDataListeningObject(this);
        this.bankTransactionSupplier.onResume();
        this.showUpdatedTransactions();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bankTransactionSupplier != null) {
            this.bankTransactionSupplier.onResume();
        }
        showUpdatedTransactions();
    }

    void showUpdatedTransactions() {
        if (this.adapter == null || this.bankTransactionSupplier == null) {
            return;
        }
        List<Transaction> transactionsToPresent = this.bankTransactionSupplier.getAllTransactions();
        this.adapter.clear();
        logger().log(Level.INFO, "Number of presented transactions: " + transactionsToPresent.size());
        for (Transaction actualTransaction : transactionsToPresent) {
            this.adapter.add(actualTransaction);
        }
        this.adapter.sort(new TransactionsComparator());
        this.adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.transaction_list, container, false);
        bookingListView = (ListView) this.mainView.findViewById(R.id.transaction_list);
        ButterKnife.bind(this, mainView);
        return this.mainView;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }


    @Override
    public void update(final Observable o, final Object arg) {
        showUpdatedTransactions();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.bankTransactionSupplier.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void notifyDataChanged() {
        this.showUpdatedTransactions();
    }
}

