package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.adorsys.api.JerseyClientUtility;
import de.fau.amos.virtualledger.server.banking.adorsys.api.json.BankAccessJSONBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
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
public class HttpBankAccessEndpoint implements BankAccessEndpoint {
    private static final int HTTP_OK_RESPONSE = 201;
    private static final int HTTP_OK = 200;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BankingApiUrlProvider urlProvider;
    private final KeycloakUtilizer keycloakUtilizer;

    @Autowired
    public HttpBankAccessEndpoint(final BankingApiUrlProvider urlProvider, final KeycloakUtilizer keycloakUtilizer) {
        this.urlProvider = urlProvider;
        this.keycloakUtilizer = keycloakUtilizer;
    }

    @Override
    public List<BankAccessBankingModel> getBankAccesses(final String userId) throws BankingException {

        // Create Jersey client
        final Client client = JerseyClientUtility.getLoggingClient(LOGGER);

        final String url = urlProvider.getBankAccessEndpointUrl(userId);
        final WebTarget webTarget = client.target(url);
        final Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", keycloakUtilizer.getAuthorizationHeader());
        final Response response = invocationBuilder.get();

        if (response.getStatus() != HTTP_OK) {
            throw new BankingException("No connection to Adorsys Server!");
        }
        final BankAccessJSONBankingModel responseModel = response.readEntity(BankAccessJSONBankingModel.class);
        if (responseModel == null || responseModel.getEmbedded() == null) {
            LOGGER.info("No access found!");
            return new ArrayList<>();
        }
        return responseModel.getEmbedded().getBankAccessEntityList();
    }

    @Override
    public BankAccessBankingModel addBankAccess(final String userId, final BankAccessBankingModel bankAccess) throws BankingException {

        List<BankAccessBankingModel> accessBeforeAdd = getBankAccesses(userId);

        // Create Jersey client
        final Client client = JerseyClientUtility.getLoggingClient(LOGGER);

        final String url = urlProvider.getBankAccessEndpointUrl(userId);
        final WebTarget webTarget = client.target(url);
        final Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", keycloakUtilizer.getAuthorizationHeader());
        final Response response = invocationBuilder.post(Entity.entity(bankAccess, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() != HTTP_OK_RESPONSE) {
            throw new BankingException("Creating Banking Access failed!");
        }

        // find the added BankAccessBankingModel
        List<BankAccessBankingModel> accessAfterAdd = getBankAccesses(userId);
        for (BankAccessBankingModel bankAccessBeforeAdd : accessBeforeAdd) {
            accessAfterAdd.removeIf(p -> p.getId().equals(bankAccessBeforeAdd.getId()));
        }
        if (accessAfterAdd.size() != 1) {
            throw new BankingException("Inconsistency after add! Added access was not found!");
        }
        return accessAfterAdd.get(0);

    }
}
