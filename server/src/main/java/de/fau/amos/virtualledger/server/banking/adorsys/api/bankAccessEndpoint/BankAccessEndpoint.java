package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankAccessEndpoint {

    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException;

    public void addBankAccess(String userId, BankAccessBankingModel bankAccess) throws BankingException;
}