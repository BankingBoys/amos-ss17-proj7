package de.fau.amos.virtualledger.server.api.modelFactories;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 20.05.2017.
 */
@ApplicationScoped
public class BankAccessFactory {

    public BankAccess createBankAccess(BankAccessBankingModel bankingModel)
    {
        BankAccess bankAccess = new BankAccess(bankingModel.getId(), bankingModel.getBankName());
        return bankAccess;
    }

    public List<BankAccess> createBankAccesses(List<BankAccessBankingModel> bankingModelList)
    {
        List<BankAccess> bankAccessesResult = new ArrayList<BankAccess>();

        for (BankAccessBankingModel bankingModel: bankingModelList) {
            bankAccessesResult.add(this.createBankAccess(bankingModel));
        }
        return bankAccessesResult;
    }
}
