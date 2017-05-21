package de.fau.amos.virtualledger.server.banking.api.json;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;

import java.util.ArrayList;
import java.util.List;

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
