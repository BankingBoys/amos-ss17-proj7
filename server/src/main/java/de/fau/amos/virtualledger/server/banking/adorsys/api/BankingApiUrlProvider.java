package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class for providing URLs for adorsys api.
 * Uses the BankingApiConfiguration for building the URLs
 */
@Component
@Scope("request")
public class BankingApiUrlProvider {

    private BankingApiConfiguration configuration;


    @Autowired
    public BankingApiUrlProvider(BankingApiConfiguration configuration)
    {
        this.configuration = configuration;
    }
    // protected empty constructor required for server to init it
    protected BankingApiUrlProvider() {
        this(null);
    }


    public String getUserEndpointUrl()
    {
        return configuration.getBankingApiUrlAbsolute() + configuration.getUserApiUrlRelative();
    }

    public String getBankAccessEndpointUrl(String userId)
    {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccessApiUrlRelative();
        url = url.replaceAll("\\{userId\\}", userId);
        return url;
    }

    public String getBankAccountEndpointUrl(String userId, String bankAccessId)
    {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountApiUrlRelative();
        url = url.replaceAll("\\{userId\\}", userId);
        url = url.replaceAll("\\{accessId\\}", bankAccessId);
        return url;
    }

    public String getBankAccountSyncEndpointUrl(String userId, String bankAccessId, String bankAccountId)
    {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountSyncApiUrlRelative();
        url = url.replaceAll("\\{userId\\}", userId);
        url = url.replaceAll("\\{accessId\\}", bankAccessId);
        url = url.replaceAll("\\{accountId\\}", bankAccountId);
        return url;
    }
}
