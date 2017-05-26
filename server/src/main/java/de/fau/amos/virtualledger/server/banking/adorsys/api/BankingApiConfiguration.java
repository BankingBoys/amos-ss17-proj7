package de.fau.amos.virtualledger.server.banking.adorsys.api;

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

    /**
     * flag if dummy endpoint should be used instead of adressing api of adorsys for user management
     */
    private final boolean useUserEndpointDummy = false;

    /**
     * flag if dummy endpoint should be used instead of adressing api of adorsys for bank accesses
     */
    private final boolean useBankAccessEndpointDummy = false;

    /**
     * flag if dummy endpoint should be used instead of adressing api of adorsys for bank accounts
     */
    private final boolean useBankAccountEndpointDummy = false;

    /**
     * flag if instead of real user on adorsys api, a specific test user with provided test data should be used
     */
    private final boolean useTestUser = false;

    /**
     * username of the test user on adorsys api
     */
    private final String testUserName = "test";



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

    public boolean isUseTestUser() {
        return useTestUser;
    }

    public String getTestUserName() {
        return testUserName;
    }
}
