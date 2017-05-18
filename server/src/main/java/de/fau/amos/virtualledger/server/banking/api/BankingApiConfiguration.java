package de.fau.amos.virtualledger.server.banking.api;

/**
 *
 * Created by Georg on 18.05.2017.
 */

import javax.enterprise.context.ApplicationScoped;

/**
 * Class that holds all configuration required for the banking api access
 * examples: URL of the banking api, dummy configuration, ...
 */
@ApplicationScoped
public class BankingApiConfiguration {


    private final String bankingApiUrl = "https://multibanking-service.dev.adorsys.de/api/v1/";

    private final boolean useUserEndpointDummy = true;
    private final boolean useBankAccountEndpointDummy = true;
    private final boolean useBankAccessEndpointDummy = true;


    public String getBankingApiUrl() {
        return bankingApiUrl;
    }

    public boolean isUseUserEndpointDummy() {
        return useUserEndpointDummy;
    }

    public boolean isUseBankAccountEndpointDummy() {
        return useBankAccountEndpointDummy;
    }

    public boolean isUseBankAccessEndpointDummy() {
        return useBankAccessEndpointDummy;
    }
}
