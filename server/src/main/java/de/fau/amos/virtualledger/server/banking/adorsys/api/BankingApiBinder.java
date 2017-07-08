package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;

/**
 * Class that is responsible for binding the right implementation of an
 * interface for banking api Chosen can be a dummy or the real implementation,
 * which depends on BankingApiConfiguration test user name.
 */
@Component

public class BankingApiBinder {

    // all injected by constructor
    private BankAccessEndpoint httpBankAccessEndpoint;
    private BankAccessEndpoint dummyBankAccessEndpoint;
    private BankAccountEndpoint httpBankAccountEndpoint;
    private BankAccountEndpoint dummyBankAccountEndpoint;

    private BankingApiConfiguration bankingApiConfiguration;

    @Autowired
    public BankingApiBinder(BankingApiConfiguration bankingApiConfiguration,
            @Qualifier("default") BankAccessEndpoint httpBankAccessEndpoint,
            @Qualifier("dummy") BankAccessEndpoint dummyBankAccessEndpoint,
            @Qualifier("default") BankAccountEndpoint httpBankAccountEndpoint,
            @Qualifier("dummy") BankAccountEndpoint dummyBankAccountEndpoint) {
        this.bankingApiConfiguration = bankingApiConfiguration;

        this.httpBankAccessEndpoint = httpBankAccessEndpoint;
        this.dummyBankAccessEndpoint = dummyBankAccessEndpoint;
        this.httpBankAccountEndpoint = httpBankAccountEndpoint;
        this.dummyBankAccountEndpoint = dummyBankAccountEndpoint;
    }

    protected BankingApiBinder() {
    }

    public BankAccessEndpoint getBankAccessEndpoint(String userId) {

        if (bankingApiConfiguration.getTestUserName().equals(userId)) {
            return dummyBankAccessEndpoint;
        } else {
            return httpBankAccessEndpoint;
        }
    }

    public BankAccountEndpoint getBankAccountEndpoint(String userId) {
        if (bankingApiConfiguration.getTestUserName().equals(userId)) {
            return dummyBankAccountEndpoint;
        } else {
            return httpBankAccountEndpoint;
        }
    }

}
