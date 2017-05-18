package de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpBankAccessEndpoint implements BankAccessEndpoint {

    @Inject
    BankingApiUrlProvider urlProvider;

    @Override
    public String testEndpointMethod1() {
        // here speak to the real endpoint
        String endpoint = urlProvider.getBankAccessEndpointUrl();

        return "HttpBankAccessEndpoint";
    }
}
