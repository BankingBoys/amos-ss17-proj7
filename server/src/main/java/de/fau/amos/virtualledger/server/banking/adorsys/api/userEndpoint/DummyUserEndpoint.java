package de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;

@Component
@Scope("request")
@BankingApiDummy
public class DummyUserEndpoint implements UserEndpoint {

    @Override
    public void createUser(String userId) {
        // nothing to do here
    }
}
