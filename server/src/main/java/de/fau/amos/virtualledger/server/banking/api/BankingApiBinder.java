package de.fau.amos.virtualledger.server.banking.api;

/**
 * Created by Georg on 18.05.2017.
 */

import de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.api.userEndpoint.UserEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 * Class that is responsible for binding the right implementation of an interface for banking api
 * Chosen can be a dummy or the real implementation, which depends on BankingApiConfiguration.
 */
@ApplicationScoped
public class BankingApiBinder {


    // all injected by setter
    private UserEndpoint userEndpoint;
    private BankAccountEndpoint bankAccountEndpoint;
    private BankAccessEndpoint bankAccessEndpoint;


    public UserEndpoint getUserEndpoint() {
        return userEndpoint;
    }

    @Inject
    public void setUserEndpoint(@BankingApiDummy UserEndpoint dummyEndpoint, @Default UserEndpoint httpEndpoint, BankingApiConfiguration bankingApiConfiguration) {
        if(bankingApiConfiguration.isUseUserEndpointDummy())
        {
            this.userEndpoint = dummyEndpoint;
        } else
        {
            this.userEndpoint = httpEndpoint;
        }
    }

    public BankAccountEndpoint getBankAccountEndpoint() {
        return bankAccountEndpoint;
    }

    @Inject
    public void setBankAccountEndpoint(@BankingApiDummy BankAccountEndpoint dummyEndpoint, @Default BankAccountEndpoint httpEndpoint, BankingApiConfiguration bankingApiConfiguration) {
        if(bankingApiConfiguration.isUseBankAccountEndpointDummy())
        {
            this.bankAccountEndpoint = dummyEndpoint;
        } else
        {
            this.bankAccountEndpoint = httpEndpoint;
        }
    }

    public BankAccessEndpoint getBankAccessEndpoint() {
        return bankAccessEndpoint;
    }

    @Inject
    public void setBankAccessEndpoint(@BankingApiDummy BankAccessEndpoint dummyEndpoint, @Default BankAccessEndpoint httpEndpoint, BankingApiConfiguration bankingApiConfiguration) {
        if(bankingApiConfiguration.isUseBankAccessEndpointDummy())
        {
            this.bankAccessEndpoint = dummyEndpoint;
        } else
        {
            this.bankAccessEndpoint = httpEndpoint;
        }
    }

}
