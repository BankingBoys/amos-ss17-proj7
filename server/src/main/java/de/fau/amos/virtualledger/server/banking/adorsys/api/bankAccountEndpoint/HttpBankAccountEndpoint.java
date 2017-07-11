package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccountJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccountSyncJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component

@Qualifier("default")
public class HttpBankAccountEndpoint implements BankAccountEndpoint {
    private static final int HTTP_OK = 200;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BankingApiUrlProvider urlProvider;
    private final KeycloakUtilizer keycloakUtilizer;

    @Autowired
    public HttpBankAccountEndpoint(final BankingApiUrlProvider urlProvider, final KeycloakUtilizer keycloakUtilizer) {
        this.urlProvider = urlProvider;
        this.keycloakUtilizer = keycloakUtilizer;
    }

    @Override
    public List<BankAccountBankingModel> getBankAccounts(final String userId, final String bankingAccessId)
            throws BankingException {

        // Create Jersey client
        final Client client = ClientBuilder.newClient();

        final String url = urlProvider.getBankAccountEndpointUrl(userId, bankingAccessId);
        final WebTarget webTarget = client.target(url);
        final Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", keycloakUtilizer.getAuthorizationHeader());
        final Response response = invocationBuilder.get();

        if (response.getStatus() != HTTP_OK) {
            LOGGER.warn("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
        final BankAccountJSONBankingModel responseModel = response.readEntity(BankAccountJSONBankingModel.class);
        if (responseModel == null || responseModel.getEmbedded() == null) {
            LOGGER.info("No accounts found");
            return new ArrayList<>();
        }
        return responseModel.getEmbedded().getBankAccountEntityList();
    }

    @Override
    public List<BookingModel> syncBankAccount(final String userId, final String bankAccessId, final String bankAccountId, final String pin)
            throws BankingException {

        // Create Jersey client
        final Client client = ClientBuilder.newClient();

        final String url = urlProvider.getBankAccountSyncEndpointUrl(userId, bankAccessId, bankAccountId);
        final WebTarget webTarget = client.target(url);
        final Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", keycloakUtilizer.getAuthorizationHeader());
        final Response response = invocationBuilder.put(Entity.entity(pin, MediaType.TEXT_PLAIN_TYPE));

        if (response.getStatus() != HTTP_OK) {
            LOGGER.warn("No connection to Adorsys Server!");
            throw new BankingException("No connection to Adorsys Server!");
        }
        final BankAccountSyncJSONBankingModel responseModel = response.readEntity(BankAccountSyncJSONBankingModel.class);
        if (responseModel == null || responseModel.getEmbedded() == null) {
            LOGGER.warn("No bookings found");
            return new ArrayList<>();
        }
        return responseModel.getEmbedded().getBookingEntityList();

    }

}
