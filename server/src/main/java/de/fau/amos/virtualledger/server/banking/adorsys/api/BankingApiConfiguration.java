package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.stereotype.Component;

/**
 * Class that holds all configuration required for the banking api access
 * examples: URL of the banking api, dummy configuration, ...
 */
@Component

public class BankingApiConfiguration {


    private static final String BANKING_API_URL_ABSOLUTE = "https://multibanking-service.dev.adorsys.de:443/api/v1/";
    private static final String BANK_ACCESS_API_URL_RELATIVE = "bankaccesses";
    private static final String BANK_ACCOUNT_API_URL_RELATIVE = "bankaccesses/{accessId}/accounts";
    private static final String BANK_ACCOUNT_SYNC_API_URL_RELATIVE = "bankaccesses/{accessId}/accounts/{accountId}/sync";

    /**
     * username of the test user that does not use adorsys api but dummies
     */
    private static final String TEST_USER_NAME = "test@user.de";



    public String getBankingApiUrlAbsolute() {
        return BANKING_API_URL_ABSOLUTE;
    }

    public String getBankAccessApiUrlRelative() {
        return BANK_ACCESS_API_URL_RELATIVE;
    }

    public String getBankAccountApiUrlRelative() {
        return BANK_ACCOUNT_API_URL_RELATIVE;
    }

    public String getBankAccountSyncApiUrlRelative() {
        return BANK_ACCOUNT_SYNC_API_URL_RELATIVE;
    }


    public String getTestUserName() {
        return TEST_USER_NAME;
    }
}
