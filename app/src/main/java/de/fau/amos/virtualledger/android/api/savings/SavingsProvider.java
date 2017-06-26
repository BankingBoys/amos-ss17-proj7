package de.fau.amos.virtualledger.android.api.savings;


import java.util.List;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;

public interface SavingsProvider {

    Observable<List<SavingsAccount>> getSavingAccounts();

    Observable<String> addSavingAccount(SavingsAccount savingsAccount);
}
