package de.fau.amos.virtualledger.server.banking.api;

/**
 *
 * Created by Georg on 18.05.2017.
 */

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Class that can be used in other controllers to do interaction with banking api.
 */
@ApplicationScoped
public class BankingApiFacade {

    @Inject
    BankingApiBinder binder;

    public String getTest()
    {
        return binder.getTestEndpoint().testEndpointMethod1();
    }
}
