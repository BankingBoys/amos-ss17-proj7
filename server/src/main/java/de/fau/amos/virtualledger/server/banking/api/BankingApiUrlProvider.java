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

    public String getBankAccessEndpointUrl()
    {
        return configuration.getBankingApiUrlAbsolute() + configuration.getBankAccessApiUrlRelative();
    }

    public String getBankAccountEndpointUrl()
    {
        return configuration.getBankingApiUrlAbsolute() + configuration.getBankAccountApiUrlRelative();
    }
}
