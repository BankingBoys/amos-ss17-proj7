package de.fau.amos.virtualledger.server.banking.api.json;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import java.util.List;

/**
 * Created by Georg on 20.05.2017.
 */
public class EmbeddedBankAccessJSONBankingModel {

    private List<BankAccessBankingModel> bankaccessentitylist;

    public EmbeddedBankAccessJSONBankingModel() {
    }

    public List<BankAccessBankingModel> getBankAccessEntityList() {
        return bankaccessentitylist;
    }

    public void setBankAccessEntityList(List<BankAccessBankingModel> bankAccessEntityList) {
        this.bankaccessentitylist = bankAccessEntityList;
    }
}
