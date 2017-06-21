package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

/**
 * Created by sebastian on 17.06.17.
 */

public class BankTransactionSupplierImplementation implements BankTransactionSupplier, Observer {
    @Inject
    BankingDataManager bankingDataManager;
    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    AuthenticationProvider authenticationProvider;

    private List<DataListening> dataListenings = new ArrayList<>();

    private ArrayList<Transaction> allBankTransactions = new ArrayList<>();

    private List<BankAccountBookings> bankAccountBookingsList;

    public BankTransactionSupplierImplementation(Activity activity, List<BankAccountBookings> bankAccountBookingsList) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
        this.bankAccountBookingsList = bankAccountBookingsList;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return this.allBankTransactions;
    }

    @Override
    public void onResume() {
        this.logger().log(Level.INFO, "Registering to bank data manager");
        bankingDataManager.addObserver(this);
        this.logger().log(Level.INFO, "BankDataMangerSyncStatus" + this.bankingDataManager.getSyncStatus());
        switch (bankingDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                bankingDataManager.sync();
                break;
            case SYNCED:
                onBookingsUpdated();
                break;
        }
    }


    private void onBookingsUpdated() {
        this.logger().info("bookings loaded. Compiling it to transactions");
        this.allBankTransactions.clear();
        for (BankAccountBookings bankAccountBookings : bankAccountBookingsList) {
            for (Booking booking : bankAccountBookings.getBookings()) {
                Transaction transaction = new Transaction(
                        bankAccessCredentialDB
                                .getAccountName(
                                        authenticationProvider.getEmail(),
                                        bankAccountBookings.getBankaccessid(),
                                        bankAccountBookings.getBankaccountid()),
                        bankAccountBookings.getBankaccountid(),
                        booking);

                this.allBankTransactions.add(transaction);
            }
        }
        this.logger().info("Notifying observers: " + dataListenings.size() + " with number of transactions: " + this.allBankTransactions.size());
        notifyObservers();
    }

    @Override
    public void addDataListeningObject(DataListening observer) {
        this.dataListenings.add(observer);
    }

    @Override
    public void deregister(DataListening observer) {
        this.dataListenings.remove(observer);
    }

    @Override
    public void onPause() {
        this.logger().log(Level.INFO, "De-Registering from bank data manager");
        this.bankingDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.onBookingsUpdated();
        notifyObservers();
    }

    private void notifyObservers() {
        for (DataListening dataListening : this.dataListenings) {
            dataListening.notifyDataChanged();
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }
}
