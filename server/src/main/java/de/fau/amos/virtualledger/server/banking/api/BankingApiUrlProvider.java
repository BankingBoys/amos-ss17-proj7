package de.fau.amos.virtualledger.server.banking.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by Georg on 18.05.2017.
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
}
