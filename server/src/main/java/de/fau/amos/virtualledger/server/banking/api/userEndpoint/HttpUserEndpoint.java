package de.fau.amos.virtualledger.server.banking.api.userEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpUserEndpoint implements UserEndpoint {

    @Inject
    BankingApiUrlProvider urlProvider;


    @Override
    public void createUser(String userId) {

    }

}
