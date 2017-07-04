package de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint;

import org.springframework.stereotype.Component;

@Component
public interface UserEndpoint {

    void createUser(String userId);

}
