package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;


@Component
@Scope("request")
public class BankAccessFactory {

    public BankAccess createBankAccess(BankAccessBankingModel bankingModel)
    {
        BankAccess bankAccess = new BankAccess(bankingModel.getId(), bankingModel.getBankName(), bankingModel.getBankCode(), bankingModel.getBankLogin());
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
