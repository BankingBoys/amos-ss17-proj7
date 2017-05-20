package de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @Default
public class HttpBankAccessEndpoint implements BankAccessEndpoint {

    @Inject
    BankingApiUrlProvider urlProvider;

    @Override
    public List<BankAccessBankingModel> getBankAccesses(String userId) {
        return null;
    }
}
