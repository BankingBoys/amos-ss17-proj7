package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

import java.util.List;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

/**
 * Created by Georg on 21.05.2017.
 */
public class EmbeddedBankAccountJSONBankingModel {

    private List<BankAccountBankingModel> bankAccountEntityList;

    public EmbeddedBankAccountJSONBankingModel() {
    }

    public List<BankAccountBankingModel> getBankAccountEntityList() {
        return bankAccountEntityList;
    }

    public void setBankAccountEntityList(List<BankAccountBankingModel> bankAccountEntityList) {
        this.bankAccountEntityList = bankAccountEntityList;
    }
}
