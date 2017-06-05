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
    private ArrayList<BankAccountSync> bankAccountSyncs = new ArrayList<>();
    private TransactionAdapter adapter;
    private View mainView;

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
        this.mainView = inflater.inflate(R.layout.fragment_transaction_overview, container, false);

        ListView bookingListView = (ListView) this.mainView.findViewById(R.id.transaction_list);
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
                                Transaction transaction = new Transaction(
                                        frag.bankingProvider
                                                .getBankAccountNameFor(
                                                        bankAccountBookings.getBankaccessid()),
                                        booking);
                                frag.adapter.add(transaction);
                                frag.refreshTotalAmount();
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

        this.adapter = new TransactionAdapter(this.getActivity(), R.id.transaction_list, new ArrayList<Transaction>());
        bookingListView.setAdapter(adapter);

        refreshTotalAmount();
        return this.mainView;
    }

    private void repaint() {

    }

    private void refreshTotalAmount() {
        double totalAmount = 0;
        for (int i = 0; i < this.adapter.getCount(); i++) {
            Transaction transaction = this.adapter.getItem(i);
            totalAmount += transaction.booking().getAmount();
        }
        this.sumView = (TextView) this.mainView.findViewById(R.id.transaction_sum_text);
        sumView.setText("Total amount: " + totalAmount);
    }


}
