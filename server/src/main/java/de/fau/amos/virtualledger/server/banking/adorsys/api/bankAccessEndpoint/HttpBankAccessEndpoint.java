package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccessJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("request")
public class HttpBankAccessEndpoint implements BankAccessEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    BankingApiUrlProvider urlProvider;

    @Override
    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException {

        // Create Jersey client
        Client client = ClientBuilder.newClient();

        String url = urlProvider.getBankAccessEndpointUrl(userId);
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();

        if (response.getStatus() != 200) {
            throw new BankingException("No connection to Adorsys Server!");
        }
        BankAccessJSONBankingModel reponseModel = response.readEntity(BankAccessJSONBankingModel.class);
        if(reponseModel == null || reponseModel.get_embedded() == null)
        {
        	logger.info("No access found!");
            return new ArrayList<BankAccessBankingModel>();
        }
        List<BankAccessBankingModel> result = reponseModel.get_embedded().getBankAccessEntityList();
        return result;
    }

    @Override
    public void addBankAccess(String userId, BankAccessBankingModel bankAccess) throws BankingException {

        // Create Jersey client
        Client client = ClientBuilder.newClient();

        String url = urlProvider.getBankAccessEndpointUrl(userId);
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(bankAccess, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() != 201) {
            throw new BankingException("Creating Banking Access failed!");
        }
    }
}
