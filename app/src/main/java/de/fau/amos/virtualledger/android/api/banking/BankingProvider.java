package de.fau.amos.virtualledger.android.api.banking;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.Observable;

public interface BankingProvider {

    Observable<List<BankAccess>> getBankingOverview();

    Observable<BankAccountSyncResult> getBankingTransactions(List<BankAccountSync> bankAccountSyncList);

    Observable<BankAccess> addBankAccess(BankAccessCredential bankAccessCredential);

    Observable<Void> deleteBankAccess(String accessId);

    Observable<Void> deleteBankAccount(String accessId, String accountId);

}
