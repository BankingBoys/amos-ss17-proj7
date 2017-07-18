package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankAccessEndpoint {

    List<BankAccessBankingModel> getBankAccesses() throws BankingException;

    BankAccessBankingModel addBankAccess(BankAccessBankingModel bankAccess) throws BankingException;
}
