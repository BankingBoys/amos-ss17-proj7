package de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiUrlProvider;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
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
        return null;
    }
}
