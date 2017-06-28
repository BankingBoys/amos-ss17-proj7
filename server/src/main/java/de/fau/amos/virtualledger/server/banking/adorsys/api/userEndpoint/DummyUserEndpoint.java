package de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.RequestScoped;

@Component
@Scope("request")
@BankingApiDummy
public class DummyUserEndpoint implements UserEndpoint {

    @Override
    public void createUser(String userId) {
        // nothing to do here
    }
}
