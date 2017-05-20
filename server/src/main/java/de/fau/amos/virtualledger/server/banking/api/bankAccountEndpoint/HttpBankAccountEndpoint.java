package de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
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
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) {


        return new ArrayList<BankAccountBankingModel>();
    }
}
