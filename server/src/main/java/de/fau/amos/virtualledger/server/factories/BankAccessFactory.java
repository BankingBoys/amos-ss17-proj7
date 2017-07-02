package de.fau.amos.virtualledger.server.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

@Component

public class BankAccessFactory {

    public BankAccess createBankAccess(BankAccessBankingModel bankingModel) {
        BankAccess bankAccess = new BankAccess(bankingModel.getId(), bankingModel.getBankName(),
                bankingModel.getBankCode(), bankingModel.getBankLogin());
        return bankAccess;
    }

    public List<BankAccess> createBankAccesses(List<BankAccessBankingModel> bankingModelList) {
        List<BankAccess> bankAccessesResult = new ArrayList<BankAccess>();

        for (BankAccessBankingModel bankingModel : bankingModelList) {
            bankAccessesResult.add(this.createBankAccess(bankingModel));
        }
        return bankAccessesResult;
    }
}
