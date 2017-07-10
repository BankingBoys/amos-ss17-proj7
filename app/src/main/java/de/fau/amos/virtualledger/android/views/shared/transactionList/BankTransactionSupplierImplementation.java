package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.app.Activity;

import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.sync.ConcreteSupplier;
import de.fau.amos.virtualledger.android.api.sync.DataManager;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;

/**
 * Created by sebastian on 17.06.17.
 */

public class BankTransactionSupplierImplementation extends ConcreteSupplier<Transaction> {
    @Inject
    BankingDataManager bankingDataManager;
    @Inject
    BankAccessCredentialDB bankAccessCredentialDB;
    @Inject
    AuthenticationProvider authenticationProvider;

    private List<BankAccountBookings> bankAccountBookingsList;

    public BankTransactionSupplierImplementation(Activity activity, List<BankAccountBookings> bankAccountBookingsList) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
        this.bankAccountBookingsList = bankAccountBookingsList;
    }

    @Override
    protected DataManager<Transaction> dataManager() {
        return new TransactionDataManagerAdapter(this.bankAccountBookingsList, this.bankingDataManager, this.authenticationProvider, this.bankAccessCredentialDB);
    }
}
