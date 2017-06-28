package de.fau.amos.virtualledger.server.banking.adorsys.api;


import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.UserEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.inject.Default;

/**
 * Class that is responsible for binding the right implementation of an interface for banking api
 * Chosen can be a dummy or the real implementation, which depends on BankingApiConfiguration test user name.
 */
@Component
@Scope("request")
public class BankingApiBinder {


    // all injected by constructor
    private UserEndpoint httpUserEndpoint;
    private UserEndpoint dummyUserEndpoint;
    private BankAccessEndpoint httpBankAccessEndpoint;
    private BankAccessEndpoint dummyBankAccessEndpoint;
    private BankAccountEndpoint httpBankAccountEndpoint;
    private BankAccountEndpoint dummyBankAccountEndpoint;

    private BankingApiConfiguration bankingApiConfiguration;

    @Autowired
    public BankingApiBinder(
            BankingApiConfiguration bankingApiConfiguration,
            UserEndpoint httpUserEndpoint,
            @BankingApiDummy UserEndpoint dummyUserEndpoint,
            BankAccessEndpoint httpBankAccessEndpoint,
            @BankingApiDummy BankAccessEndpoint dummyBankAccessEndpoint,
            BankAccountEndpoint httpBankAccountEndpoint,
            @BankingApiDummy BankAccountEndpoint dummyBankAccountEndpoint)
    {
        this.bankingApiConfiguration = bankingApiConfiguration;

        this.httpUserEndpoint = httpUserEndpoint;
        this.dummyUserEndpoint = dummyUserEndpoint;
        this.httpBankAccessEndpoint = httpBankAccessEndpoint;
        this.dummyBankAccessEndpoint = dummyBankAccessEndpoint;
        this.httpBankAccountEndpoint = httpBankAccountEndpoint;
        this.dummyBankAccountEndpoint = dummyBankAccountEndpoint;
    }
    protected BankingApiBinder()    { }


    public UserEndpoint getUserEndpoint(String userId) {

        if(bankingApiConfiguration.getTestUserName().equals(userId))
        {
            return dummyUserEndpoint;
        } else
        {
            return httpUserEndpoint;
        }
    }


    public BankAccessEndpoint getBankAccessEndpoint(String userId) {

        if(bankingApiConfiguration.getTestUserName().equals(userId))
        {
            return dummyBankAccessEndpoint;
        } else
        {
            return httpBankAccessEndpoint;
        }
    }

    public BankAccountEndpoint getBankAccountEndpoint(String userId)
    {
        if(bankingApiConfiguration.getTestUserName().equals(userId))
        {
            return dummyBankAccountEndpoint;
        } else
        {
            return httpBankAccountEndpoint;
        }
    }

}
