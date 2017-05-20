package de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
public interface BankAccountEndpoint {

    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId);
}