package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;

/**
 * Created by sebastian on 17.06.17.
 */

public class SavingsSupplier implements de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier<SavingsAccount>, Observer {

    @Inject
    SavingsAccountsDataManager savingsAccountsDataManager;

    private Set<DataListening> dataListenings = new HashSet<>();

    private List<SavingsAccount> allSavings = new ArrayList<>();

    public SavingsSupplier(Activity activity) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
    }

    @Override
    public List<SavingsAccount> getAll() {
        return this.allSavings;
    }

    @Override
    public void onResume() {
        this.logger().log(Level.INFO, "Registering to bank data manager");
        savingsAccountsDataManager.addObserver(this);
        this.logger().log(Level.INFO, "BankDataMangerSyncStatus" + this.savingsAccountsDataManager.getSyncStatus());
        switch (savingsAccountsDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                savingsAccountsDataManager.sync();
                break;
            case SYNCED:
                onSavingsUpdated();
                break;
        }
    }


    private void onSavingsUpdated() {
        this.logger().info("Savings loaded.");
        this.allSavings.clear();
        List<SavingsAccount> savingAccounts = null;
        try {
            savingAccounts = this.savingsAccountsDataManager.getSavingsAccounts();
        } catch (SyncFailedException e) {
            Log.e("", "Sync failed");
        }
        if(savingAccounts != null){
            this.allSavings.addAll(savingAccounts);
        }
        notifyObservers();
    }

    @Override
    public void addDataListeningObject(DataListening observer) {
        if (this.dataListenings.isEmpty()) {
            this.savingsAccountsDataManager.addObserver(this);
        }
        this.dataListenings.add(observer);
    }

    @Override
    public void deregister(DataListening observer) {
        this.dataListenings.remove(observer);
        if (this.dataListenings.isEmpty()) {
            this.savingsAccountsDataManager.deleteObserver(this);
        }
    }

    @Override
    public void onPause() {
        this.logger().log(Level.INFO, "De-Registering from bank data manager");
        this.savingsAccountsDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.onSavingsUpdated();
    }

    private void notifyObservers() {
        this.logger().info("Notify "+this.dataListenings.size()+" Savings-Listener with "+this.allSavings.size()+" Savings accounts");
        for (DataListening dataListening : this.dataListenings) {
            dataListening.notifyDataChanged();
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }
}
