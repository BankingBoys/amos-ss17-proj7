package de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
public interface BankAccessEndpoint {

    public List<BankAccessBankingModel> getBankAccesses(String userId);
}