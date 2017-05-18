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

    private final boolean useTestDummy = false;
    private final String bankingApiUrl = "https://multibanking-service.dev.adorsys.de/api/v1/";



    public String getBankingApiUrl() {
        return bankingApiUrl;
    }

    public boolean isUseTestDummy() {
        return useTestDummy;
    }
}
