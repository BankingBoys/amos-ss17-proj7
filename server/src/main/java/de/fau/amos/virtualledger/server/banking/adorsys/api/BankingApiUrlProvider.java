package de.fau.amos.virtualledger.server.banking.adorsys.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by Georg on 18.05.2017.
 */

/**
 * Class for providing URLs for adorsys api.
 * Uses the BankingApiConfiguration for building the URLs
 */
@ApplicationScoped
public class BankingApiUrlProvider {

    private BankingApiConfiguration configuration;


    @Inject
    public void setConfiguration(BankingApiConfiguration configuration)
    {
        this.configuration = configuration;
    }


    public String getUserEndpointUrl()
    {
        return configuration.getBankingApiUrlAbsolute() + configuration.getUserApiUrlRelative();
    }

    public String getBankAccessEndpointUrl(String userId)
    {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccessApiUrlRelative();
        if(configuration.isUseTestUser()) {
            url = url.replaceAll("\\{userId\\}", configuration.getTestUserName());
        } else {
            url = url.replaceAll("\\{userId\\}", userId);
        }
        return url;
    }

    public String getBankAccountEndpointUrl(String userId, String bankAccessId)
    {
        String url = configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountApiUrlRelative();
        if(configuration.isUseTestUser()) {
            url = url.replaceAll("\\{userId\\}", "test");
        } else {
            url = url.replaceAll("\\{userId\\}", userId);
        }
        url = url.replaceAll("\\{accessId\\}", bankAccessId);
        return url;
    }
}
