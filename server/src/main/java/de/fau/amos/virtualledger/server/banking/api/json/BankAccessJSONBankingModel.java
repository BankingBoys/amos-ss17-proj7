package de.fau.amos.virtualledger.server.banking.api.json;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import java.util.List;

/**
 * Created by Georg on 20.05.2017.
 */
public class BankAccessJSONBankingModel {

    private EmbeddedBankAccessJSONBankingModel _embedded;

    public BankAccessJSONBankingModel() {
    }

    public EmbeddedBankAccessJSONBankingModel get_embedded() {
        return _embedded;
    }

}
