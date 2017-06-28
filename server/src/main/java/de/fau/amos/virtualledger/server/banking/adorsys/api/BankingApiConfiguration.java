package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class that holds all configuration required for the banking api access
 * examples: URL of the banking api, dummy configuration, ...
 */
@Component
@Scope("request")
public class BankingApiConfiguration {


    private final String bankingApiUrlAbsolute = "https://multibanking-service.dev.adorsys.de:443/api/v1/";
    private final String userApiUrlRelative = "users";
    private final String bankAccessApiUrlRelative = "users/{userId}/bankaccesses";
    private final String bankAccountApiUrlRelative = "users/{userId}/bankaccesses/{accessId}/accounts";
    private final String bankAccountSyncApiUrlRelative = "users/{userId}/bankaccesses/{accessId}/accounts/{accountId}/sync";

    /**
     * username of the test user that does not use adorsys api but dummies
     */
    private final String testUserName = "test@user.de";



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

    public String getBankAccountSyncApiUrlRelative() {
        return bankAccountSyncApiUrlRelative;
    }


    public String getTestUserName() {
        return testUserName;
    }
}
