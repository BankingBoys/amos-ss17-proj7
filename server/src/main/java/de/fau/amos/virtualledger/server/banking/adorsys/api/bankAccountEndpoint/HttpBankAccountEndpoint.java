package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import com.sun.istack.logging.Logger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccountJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpBankAccountEndpoint implements BankAccountEndpoint {

    @Inject
    BankingApiUrlProvider urlProvider;


    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException {

        // Create Jersey client
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        String url = urlProvider.getBankAccountEndpointUrl(userId, bankingAccessId);
        WebResource.Builder webResourceGET = client.resource(url)
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON);
        ClientResponse response = webResourceGET.get(ClientResponse.class);

        if (response.getStatus() != 200) {
        	Logger.getLogger(HttpBankAccountEndpoint.class).warning("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
        BankAccountJSONBankingModel reponseModel = response.getEntity(BankAccountJSONBankingModel.class);
        if(reponseModel == null || reponseModel.get_embedded() == null)
        { 
        	Logger.getLogger(HttpBankAccountEndpoint.class).info("No accounts found");
            return new ArrayList<BankAccountBankingModel>();
        }
        List<BankAccountBankingModel> result = reponseModel.get_embedded().getBankAccountEntityList();
        return result;
    }

    @Override
    public void syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException {

        // Create Jersey client
        Client client = Client.create();
        client.addFilter(new LoggingFilter(System.out));

        String url = urlProvider.getBankAccountSyncEndpointUrl(userId, bankAccessId, bankAccountId);
        WebResource webResourceGET = client.resource(url);
        ClientResponse response = webResourceGET.put(ClientResponse.class, pin);

        if (response.getStatus() != 200) {
            Logger.getLogger(HttpBankAccountEndpoint.class).warning("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
    }


}
