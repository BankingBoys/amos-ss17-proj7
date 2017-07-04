package de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint;

import java.lang.invoke.MethodHandles;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.CreateUserJSONBankingModel;

@Component
@Qualifier("default")

public class HttpUserEndpoint implements UserEndpoint {
    private static final int HTTP_OK = 201;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private BankingApiUrlProvider urlProvider;

    @Override
    public void createUser(String userId) {

        CreateUserJSONBankingModel postBody = new CreateUserJSONBankingModel();
        postBody.setId(userId);

        // Create Jersey client
        Client client = ClientBuilder.newClient();

        String url = urlProvider.getUserEndpointUrl();
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(postBody, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() != HTTP_OK) {
            LOGGER.warn("No connection to Adorsys Server!");
            throw new WebApplicationException("No connection to Adorsys Server!");
        }
    }

}
