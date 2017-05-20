package de.fau.amos.virtualledger.server.controllers;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.api.modelFactories.BankAccessFactory;
import de.fau.amos.virtualledger.server.banking.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Georg on 20.05.2017.
 */
@RequestScoped
public class BankingOverviewController {

    @Inject
    BankingApiFacade bankingApiFacade;

    @Inject
    BankAccessFactory bankAccessFactory;


    public List<BankAccess> getBankingOverview(String email)
    {
        List<BankAccessBankingModel> bankingModel = bankingApiFacade.getBankAccesses(email);
        List<BankAccess> bankAccesses = bankAccessFactory.createBankAccesses(bankingModel);
        return bankAccesses;
    }

}
