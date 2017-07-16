package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankAccountEndpoint {

    List<BankAccountBankingModel> getBankAccounts(String bankingAccessId) throws BankingException;

    List<BookingModel> syncBankAccount(String bankAccessId, String bankAccountId, String pin)
            throws BankingException;
}
