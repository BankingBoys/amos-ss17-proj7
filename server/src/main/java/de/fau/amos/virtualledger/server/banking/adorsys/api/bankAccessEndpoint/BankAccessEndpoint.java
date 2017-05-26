package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;

import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
public interface BankAccessEndpoint {

    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException;

    public void addBankAccess(String userId, BankAccessBankingModel bankAccess) throws BankingException;
}