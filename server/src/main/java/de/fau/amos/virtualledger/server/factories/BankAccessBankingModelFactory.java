package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

/**
 * Created by Georg on 22.05.2017.
 */
public class BankAccessBankingModelFactory {

    public BankAccessBankingModel createBankAccessBankingModel(String userId, BankAccessCredential bankAccessCredential)
    {
        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setUserId(userId);
        bankAccessBankingModel.setBankCode(bankAccessCredential.getBankcode());
        bankAccessBankingModel.setBankLogin(bankAccessCredential.getBanklogin());
        bankAccessBankingModel.setPin(bankAccessCredential.getPin());

        return bankAccessBankingModel;
    }
}
