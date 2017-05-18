package de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpBankAccessEndpoint implements BankAccessEndpoint {

    @Override
    public String testEndpointMethod1() {
        // here speak to the real endpoint
        return "HttpBankAccessEndpoint";
    }
}
