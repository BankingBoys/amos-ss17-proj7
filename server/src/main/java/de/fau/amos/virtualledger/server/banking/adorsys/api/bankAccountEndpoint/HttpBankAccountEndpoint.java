package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccountJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccountSyncJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpBankAccountEndpoint implements BankAccountEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    BankingApiUrlProvider urlProvider;


    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException {

        // Create Jersey client
        Client client = ClientBuilder.newClient();

        String url = urlProvider.getBankAccountEndpointUrl(userId, bankingAccessId);
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();

        if (response.getStatus() != 200) {
        	logger.warn("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
        BankAccountJSONBankingModel reponseModel = response.readEntity(BankAccountJSONBankingModel.class);
        if(reponseModel == null || reponseModel.get_embedded() == null)
        { 
        	logger.info("No accounts found");
            return new ArrayList<>();
        }
        return reponseModel.get_embedded().getBankAccountEntityList();
    }

    @Override
    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException {

        // Create Jersey client
        Client client = ClientBuilder.newClient();

        String url = urlProvider.getBankAccountSyncEndpointUrl(userId, bankAccessId, bankAccountId);
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(pin, MediaType.TEXT_PLAIN_TYPE));

        if (response.getStatus() != 200) {
            logger.warn("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
        BankAccountSyncJSONBankingModel responseModel = response.readEntity(BankAccountSyncJSONBankingModel.class);
        if(responseModel == null || responseModel.get_embedded() == null) {
            logger.warn("No bookings found");
            return new ArrayList<>();
        }
        return responseModel.get_embedded().getBookingEntityList();

    }


}
