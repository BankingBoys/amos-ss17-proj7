package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.sync.AbstractSupplier;
import de.fau.amos.virtualledger.android.api.sync.DataManager;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by sebastian on 17.06.17.
 */

public class SavingsSupplier extends AbstractSupplier<SavingsAccount> {

    @Inject
    SavingsAccountsDataManager savingsAccountsDataManager;


    public SavingsSupplier(Activity activity) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
    }


    @Override
    protected DataManager<SavingsAccount> dataManager() {
        return savingsAccountsDataManager;
    }
}
