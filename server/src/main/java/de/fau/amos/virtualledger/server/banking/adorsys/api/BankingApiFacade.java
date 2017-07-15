package de.fau.amos.virtualledger.server.banking.adorsys.api;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class that can be used in other controllers to do interaction with banking
 * api.
 */
@Component

public class BankingApiFacade {

    private BankingApiBinder binder;

    @Autowired
    public BankingApiFacade(final BankingApiBinder binder) {
        this.binder = binder;
    }

    // protected empty constructor required for server to init it
    protected BankingApiFacade() {
    }

    public List<BankAccessBankingModel> getBankAccesses(final String userId) throws BankingException {
        return binder.getBankAccessEndpoint(userId).getBankAccesses(userId);
    }

    public List<BankAccountBankingModel> getBankAccounts(final String userId, final String bankAccessId) throws BankingException {
        return binder.getBankAccountEndpoint(userId).getBankAccounts(userId, bankAccessId);
    }

    public List<BookingModel> syncBankAccount(final String userId, final String bankAccessId, final String bankAccountId, final String pin)
            throws BankingException {
        return binder.getBankAccountEndpoint(userId).syncBankAccount(userId, bankAccessId, bankAccountId, pin);
    }

    public BankAccessBankingModel addBankAccess(final String userId, final BankAccessBankingModel bankAccess) throws BankingException {
        return binder.getBankAccessEndpoint(userId).addBankAccess(userId, bankAccess);
    }

}
