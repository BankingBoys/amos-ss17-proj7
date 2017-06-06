package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment.NoBankingAccessesFragment;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
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
    private ArrayList<Transaction> allTransactions = new ArrayList<>();

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


        final BankAccessCredentialDB db = new BankAccessCredentialDB(getActivity());
        List<BankAccountSync> syncList = db.getBankAccountSyncList(authenticationProvider.getEmail());
        if(syncList == null || syncList.size() == 0){
            openFragment(new NoBankingAccessesFragment());
        }
        bankingProvider.getBankingTransactions(db.getBankAccountSyncList(authenticationProvider.getEmail()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BankAccountSyncResult>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BankAccountSyncResult bankAccountSyncResult) {
                        List<BankAccountBookings> allSyncResults = bankAccountSyncResult.getBankaccountbookings();
                        if(allSyncResults == null || allSyncResults.size() == 0) {
                            openFragment(new NoBankingAccessesFragment());
                        }
                        for (BankAccountBookings bankAccountBookings : allSyncResults) {
                            for (Booking booking : bankAccountBookings.getBookings()) {
                                Transaction transaction = new Transaction(
                                        new BankAccessCredentialDB(getActivity())
                                                .getAccountName(
                                                        authenticationProvider.getEmail(),
                                                        bankAccountBookings.getBankaccessid(),
                                                        bankAccountBookings.getBankaccountid()),
                                        booking);

                                frag.allTransactions.add(transaction);
                                Calendar calTransaction = Calendar.getInstance();
                                calTransaction.setTime(transaction.booking().getDate());

                                Calendar calToday = new GregorianCalendar();
                                if (calTransaction.get(Calendar.MONTH) == calToday.get(Calendar.MONTH)) {
                                    frag.adapter.add(transaction);
                                }

                                frag.refreshTotalAmount();
                            }
                        }
                        frag.adapter.sort(new TransactionsComparator());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, "failed to sync transactions",e);
                        Toast.makeText(getActivity(), "Problems with synchronisation of transactions. Please try again later", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

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
        for (int i = 0; i < this.allTransactions.size(); i++) {
            Transaction transaction = this.allTransactions.get(i);
            totalAmount += transaction.booking().getAmount();
        }
        this.sumView = (TextView) this.mainView.findViewById(R.id.transaction_sum_text);

        String bankBalanceString = String.format(Locale.GERMAN, "%.2f", totalAmount);
        sumView.setText("Total amount: " + bankBalanceString);
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


}
