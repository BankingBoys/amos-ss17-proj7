package de.fau.amos.virtualledger.server.banking.api;

/**
 *
 * Created by Georg on 18.05.2017.
 */

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Class that can be used in other controllers to do interaction with banking api.
 */
@ApplicationScoped
public class BankingApiFacade {


    // injected by setter
    BankingApiBinder binder;



    public void createUser(String userId)
    {
        binder.getUserEndpoint().createUser(userId);
    }

    public List<BankAccessBankingModel> getBankAccesses(String userId)
    {
        return binder.getBankAccessEndpoint().getBankAccesses(userId);
    }

    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankAccessId)
    {
        return binder.getBankAccountEndpoint().getBankAccounts(userId, bankAccessId);
    }


    @Inject
    public void setBinder(BankingApiBinder binder) {
        this.binder = binder;
    }
}
