package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

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

    public void set_embedded(EmbeddedBankAccessJSONBankingModel _embedded) {
        this._embedded = _embedded;
    }
}
