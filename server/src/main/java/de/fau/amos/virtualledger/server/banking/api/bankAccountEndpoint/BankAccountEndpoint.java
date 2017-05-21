package de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint;

import java.util.List;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

/**
 * Created by Georg on 18.05.2017.
 */
public interface BankAccountEndpoint {

    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId);
}