package de.fau.amos.virtualledger.android.api.banking;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import io.reactivex.Observable;

/**
 * Created by sebastian on 14.05.17.
 */

public interface BankingProvider {

    Observable<List<BankAccess>> getBankingOverview();

    Observable<String> addBankAccess(BankAccessCredential bankAccessCredential);

    Observable<String> deleteBankAccess(String accessId);

    Observable<String> deleteBankAccount(String accessId, String accountId);
}
