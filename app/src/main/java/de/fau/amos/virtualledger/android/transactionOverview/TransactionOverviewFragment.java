package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.Booking;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionOverviewFragment extends Fragment {
    /**
     *
     */
    private TextView sumView = null;
    private double totalAmount = 0;
    private ArrayList<BankAccountSync> bankAccountSyncs = new ArrayList<>();
    private TransactionAdapter adapter;

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
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_transaction_overview, container, false);
        refreshTotalAmount(view);

        ListView bookingListView = (ListView) view.findViewById(R.id.transaction_list);
        final TransactionOverviewFragment frag = this;
        bankingProvider.getAllBankingTransactions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Object o) {
                        List<BankAccountBookings> allSyncResults = (List<BankAccountBookings>) o;
                        for (BankAccountBookings bankAccountBookings : allSyncResults) {
                            for (Booking booking : bankAccountBookings.getBookings()) {
                                Transaction transaction = new Transaction("testbank", booking);
                                frag.adapter.add(transaction);
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "failed to sync transactions", e);
                        Toast.makeText(getActivity(), "Problems with synchronisation of transactions. PLease try again later", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        frag.adapter.sort(new TransactionsComparator());
                    }
                });

        this.adapter = new TransactionAdapter(this.getActivity(), R.id.transaction_list,  new ArrayList<Transaction>());
        bookingListView.setAdapter(adapter);

        return view;
    }

    private void repaint() {

    }

    private void refreshTotalAmount(View view) {
        this.sumView = (TextView) view.findViewById(R.id.transaction_sum_text);
        sumView.setText("Total amount: " + totalAmount);
    }


}
