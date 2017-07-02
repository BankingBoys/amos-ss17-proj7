package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import java.util.List;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.stereotype.Component;

@Component
public interface BankAccountEndpoint {

    List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException;

    List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin)
            throws BankingException;
}
