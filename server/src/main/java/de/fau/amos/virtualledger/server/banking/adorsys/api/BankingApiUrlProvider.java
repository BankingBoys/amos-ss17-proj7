package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for providing URLs for adorsys api. Uses the BankingApiConfiguration
 * for building the URLs
 */
@Component

public class BankingApiUrlProvider {

    private BankingApiConfiguration configuration;

    @Autowired
    public BankingApiUrlProvider(BankingApiConfiguration configuration) {
        this.configuration = configuration;
    }

    // protected empty constructor required for server to init it
    protected BankingApiUrlProvider() {
        this(null);
    }

    public String getBankAccessEndpointUrl() {
        return configuration.getBankingApiUrlAbsolute() + configuration.getBankAccessApiUrlRelative();
    }

    public String getBankAccountEndpointUrl(String bankAccessId) {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountApiUrlRelative();
        url = url.replaceAll("\\{accessId\\}", bankAccessId);
        return url;
    }

    public String getBankAccountSyncEndpointUrl(String bankAccessId, String bankAccountId) {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountSyncApiUrlRelative();
        url = url.replaceAll("\\{accessId\\}", bankAccessId);
        url = url.replaceAll("\\{accountId\\}", bankAccountId);
        return url;
    }
}
