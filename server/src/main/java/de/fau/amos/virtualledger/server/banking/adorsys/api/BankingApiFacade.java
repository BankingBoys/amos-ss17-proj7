package de.fau.amos.virtualledger.server.banking.adorsys.api;


import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class that can be used in other controllers to do interaction with banking api.
 */
@Component
@Scope("request")
public class BankingApiFacade {


    BankingApiBinder binder;

    @Autowired
    public BankingApiFacade(BankingApiBinder binder) {
        this.binder = binder;
    }
    // protected empty constructor required for server to init it
    protected BankingApiFacade() { }




    public void createUser(String userId)
    {
        binder.getUserEndpoint(userId).createUser(userId);
    }

    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException
    {
        return binder.getBankAccessEndpoint(userId).getBankAccesses(userId);
    }

    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankAccessId) throws BankingException
    {
        return binder.getBankAccountEndpoint(userId).getBankAccounts(userId, bankAccessId);
    }

    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException
    {
        return binder.getBankAccountEndpoint(userId).syncBankAccount(userId, bankAccessId, bankAccountId, pin);
    }

    public void addBankAccess(String userId, BankAccessBankingModel bankAccess) throws BankingException
    {
        binder.getBankAccessEndpoint(userId).addBankAccess(userId, bankAccess);
    }

}
