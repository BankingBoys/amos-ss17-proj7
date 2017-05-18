package de.fau.amos.virtualledger.server.banking.api;

/**
 *
 * Created by Georg on 18.05.2017.
 */

import de.fau.amos.virtualledger.server.banking.api.testEndpoint.TestEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Class that can be used in other controllers to do interaction with banking api.
 */
@ApplicationScoped
public class BankingApiFacade {


    // injected by setter
    BankingApiBinder binder;

    public String getTest()
    {
        TestEndpoint testEndpoint = binder.getTestEndpoint();
        return testEndpoint.testEndpointMethod1();
    }

    @Inject
    public void setBinder(BankingApiBinder binder) {
        this.binder = binder;
    }
}
