package de.fau.amos.virtualledger.android.api.banking;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;

public interface BankingProvider {

    Observable<List<BankAccess>> getBankingOverview();

    Observable<BankAccountSyncResult> getBankingTransactions(List<BankAccountSync> bankAccountSyncList);

    Observable<BankAccess> addBankAccess(BankAccessCredential bankAccessCredential);

    Observable<StringApiModel> deleteBankAccess(String accessId);

    Observable<StringApiModel> deleteBankAccount(String accessId, String accountId);

}
