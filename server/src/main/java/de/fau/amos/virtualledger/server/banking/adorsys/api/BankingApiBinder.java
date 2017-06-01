package de.fau.amos.virtualledger.server.banking.adorsys.api;

/**
 * Created by Georg on 18.05.2017.
 */


import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.HttpBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.HttpBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.DummyUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.HttpUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.UserEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 * Class that is responsible for binding the right implementation of an interface for banking api
 * Chosen can be a dummy or the real implementation, which depends on BankingApiConfiguration test user name.
 */
@ApplicationScoped
public class BankingApiBinder {


    // all injected by constructor
    private UserEndpoint httpUserEndpoint;
    private UserEndpoint dummyUserEndpoint;
    private BankAccessEndpoint httpBankAccessEndpoint;
    private BankAccessEndpoint dummyBankAccessEndpoint;
    private BankAccountEndpoint httpBankAccountEndpoint;
    private BankAccountEndpoint dummyBankAccountEndpoint;

    private BankingApiConfiguration bankingApiConfiguration;

    @Inject
    public BankingApiBinder(
            BankingApiConfiguration bankingApiConfiguration,
            @Default UserEndpoint httpUserEndpoint,
            @BankingApiDummy UserEndpoint dummyUserEndpoint,
            @Default BankAccessEndpoint httpBankAccessEndpoint,
            @BankingApiDummy BankAccessEndpoint dummyBankAccessEndpoint,
            @Default BankAccountEndpoint httpBankAccountEndpoint,
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
