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


    private final String bankingApiUrlAbsolute = "https://multibanking-service.dev.adorsys.de:443/api/v1/";
    private final String userApiUrlRelative = "users";
    private final String bankAccessApiUrlRelative = "users/{userId}/bankaccesses";
    private final String bankAccountApiUrlRelative = "users/{userId}/bankaccesses/{accessId}/accounts";

    private final boolean useUserEndpointDummy = false;
    private final boolean useBankAccessEndpointDummy = false;
    private final boolean useBankAccountEndpointDummy = false;



    public String getBankingApiUrlAbsolute() {
        return bankingApiUrlAbsolute;
    }

    public String getUserApiUrlRelative() {
        return userApiUrlRelative;
    }

    public String getBankAccessApiUrlRelative() {
        return bankAccessApiUrlRelative;
    }

    public String getBankAccountApiUrlRelative() {
        return bankAccountApiUrlRelative;
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
