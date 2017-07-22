package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Class that holds all configuration required for the banking api access
 * examples: URL of the banking api, dummy configuration, ...
 */
@SuppressWarnings("SpellCheckingInspection")
@Component
public class BankingApiConfiguration {


    private static final String BANKING_API_URL_ABSOLUTE = "https://multibanking-service.dev.adorsys.de:443/api/v1/";
    private static final String BANK_ACCESS_API_URL_RELATIVE = "bankaccesses";
    private static final String BANK_ACCOUNT_API_URL_RELATIVE = "bankaccesses/{accessId}/accounts";
    private static final String BANK_ACCOUNT_SYNC_API_URL_RELATIVE = "bankaccesses/{accessId}/accounts/{accountId}/sync";

    /**
     * username of the test user that does not use adorsys api but dummies
     */
    private static final List<String> TEST_USER_NAMES = Arrays.asList("test@user.de", "test@user2.de");



    String getBankingApiUrlAbsolute() {
        return BANKING_API_URL_ABSOLUTE;
    }

    String getBankAccessApiUrlRelative() {
        return BANK_ACCESS_API_URL_RELATIVE;
    }

    String getBankAccountApiUrlRelative() {
        return BANK_ACCOUNT_API_URL_RELATIVE;
    }

    String getBankAccountSyncApiUrlRelative() {
        return BANK_ACCOUNT_SYNC_API_URL_RELATIVE;
    }


    List<String> getTestUserNames() {
        return TEST_USER_NAMES;
    }
}
