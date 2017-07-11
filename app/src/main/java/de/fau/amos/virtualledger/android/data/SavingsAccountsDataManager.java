package de.fau.amos.virtualledger.android.data;

import de.fau.amos.virtualledger.android.api.sync.AbstractDataManager;
import de.fau.amos.virtualledger.android.api.sync.DataProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class SavingsAccountsDataManager extends AbstractDataManager<SavingsAccount> {

    public SavingsAccountsDataManager(final DataProvider<SavingsAccount> savingsProvider) {
        super(savingsProvider);
    }
}
