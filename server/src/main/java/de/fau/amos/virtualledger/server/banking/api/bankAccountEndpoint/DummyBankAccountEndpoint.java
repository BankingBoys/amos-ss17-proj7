package de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiDummy;

import javax.enterprise.context.RequestScoped;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @BankingApiDummy
public class DummyBankAccountEndpoint implements BankAccountEndpoint {

    @Override
    public String testEndpointMethod1() {
        // do here the dummy logic
        return "DummyBankAccessEndpoint";
    }
}
