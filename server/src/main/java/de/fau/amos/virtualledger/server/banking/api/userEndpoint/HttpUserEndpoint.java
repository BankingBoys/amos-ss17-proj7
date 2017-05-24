package de.fau.amos.virtualledger.server.banking.api.userEndpoint;

import com.sun.istack.logging.Logger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.api.json.CreateUserJSONBankingModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpUserEndpoint implements UserEndpoint {

    @Inject
    BankingApiUrlProvider urlProvider;


    @Override
    public void createUser(String userId) {

        CreateUserJSONBankingModel postBody = new CreateUserJSONBankingModel();
        postBody.setId(userId);

        // Create Jersey client
        Client client = Client.create();

        String url = urlProvider.getUserEndpointUrl();
        WebResource webResourcePOST = client.resource(url);
        webResourcePOST.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON);
        ClientResponse response = webResourcePOST.post(ClientResponse.class, postBody);

        if (response.getStatus() != 201) {
        	Logger.getLogger(HttpUserEndpoint.class).warning("No connection to Adorsys Server!");
            throw new WebApplicationException("No connection to Adorsys Server!");
        }
    }

}
