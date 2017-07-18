package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.sync.DataManager;
import de.fau.amos.virtualledger.android.api.sync.ServerCallStatusHandler;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

/**
 * Created by sebastian on 10.07.17.
 */

class TransactionDataManagerAdapter implements DataManager<Transaction> {

    private BankingDataManager bankingDataManager;
    private BankAccessCredentialDB bankAccessCredentialDB;
    private AuthenticationProvider authenticationProvider;
    private List<BankAccountBookings> bankAccountBookingsList;


    public TransactionDataManagerAdapter(List<BankAccountBookings> bankAccountBookingsList, BankingDataManager bankingDataManager, AuthenticationProvider authenticationProvider, BankAccessCredentialDB bankAccessCredentialDB) {
        this.bankAccountBookingsList = bankAccountBookingsList;
        this.bankingDataManager = bankingDataManager;
        this.authenticationProvider = authenticationProvider;
        this.bankAccessCredentialDB = bankAccessCredentialDB;
    }


    @Override
    public void sync() {
        this.bankingDataManager.sync();
    }

    @Override
    public SyncStatus getSyncStatus() {
        return this.bankingDataManager.getSyncStatus();
    }

    @Override
    public List<Transaction> getAll() throws SyncFailedException {

        List<Transaction> allBankTransactions = new ArrayList<>();
        for (BankAccountBookings bankAccountBookings : bankAccountBookingsList) {
            for (Booking booking : bankAccountBookings.getBookings()) {
                Transaction transaction = new Transaction(
                        bankAccessCredentialDB
                                .getAccountName(
                                        authenticationProvider.getUserId(),
                                        bankAccountBookings.getBankaccessid(),
                                        bankAccountBookings.getBankaccountid()),
                        bankAccountBookings.getBankaccountid(),
                        booking);

                allBankTransactions.add(transaction);
            }
        }
        return allBankTransactions;
    }

    @Override
    public void add(Transaction newItem, ServerCallStatusHandler h) {
    }

    @Override
    public void addObserver(Observer o) {
        this.bankingDataManager.addObserver(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        this.bankingDataManager.deleteObserver(o);
    }

    @Override
    public void delete(Transaction item, ServerCallStatusHandler h) {
    }
}
