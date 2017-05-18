package de.fau.amos.virtualledger.server.banking.api.testEndpoint;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpTestEndpoint implements TestEndpoint {

    @Override
    public String testEndpointMethod1() {
        // here speak to the real endpoint
        return "HttpTestEndpoint";
    }
}
