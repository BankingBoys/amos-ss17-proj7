package de.fau.amos.virtualledger.server.banking.api;

/**
 * Created by Georg on 18.05.2017.
 */

import de.fau.amos.virtualledger.server.banking.api.testEndpoint.TestEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Class that is responsible for binding the right implementation of an interface for banking api
 * Chosen can be a dummy or the real implementation, which depends on BankingApiConfiguration.
 */
@ApplicationScoped
public class BankingApiBinder {

    @Inject
    private BankingApiConfiguration configuration;

    private TestEndpoint testEndpoint;


    public TestEndpoint getTestEndpoint()
    {
        return testEndpoint;
    }

    @Inject
    public void setTestEndpoint(@BankingApiDummy TestEndpoint dummyEndpoint, @Default TestEndpoint httpEndpoint)
    {
        if(configuration.isUseTestDummy())
        {
            this.testEndpoint = dummyEndpoint;
        } else
        {
            this.testEndpoint = httpEndpoint;
        }
    }

}
