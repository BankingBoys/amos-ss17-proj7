package de.fau.amos.virtualledger.server.banking.api.userEndpoint;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpUserEndpoint implements UserEndpoint {

    @Override
    public String testEndpointMethod1() {
        // here speak to the real endpoint
        return "HttpBankAccessEndpoint";
    }
}
