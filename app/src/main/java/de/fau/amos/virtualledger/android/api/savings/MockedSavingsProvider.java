package de.fau.amos.virtualledger.android.api.savings;

import java.util.List;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;


public class MockedSavingsProvider implements SavingsProvider {



    @Override
    public Observable<List<SavingsAccount>> getSavingAccounts() {
        return null;
    }

    @Override
    public Observable<String> addSavingAccounts(List<SavingsAccount> savingsAccounts) {
        return null;
    }
}
