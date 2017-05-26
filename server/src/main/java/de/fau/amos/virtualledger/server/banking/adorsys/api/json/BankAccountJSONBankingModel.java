package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

/**
 * Created by Georg on 21.05.2017.
 */
public class BankAccountJSONBankingModel {

    private EmbeddedBankAccountJSONBankingModel _embedded;

    public BankAccountJSONBankingModel() {
    }

    public EmbeddedBankAccountJSONBankingModel get_embedded() {
        return _embedded;
    }

    public void set_embedded(EmbeddedBankAccountJSONBankingModel _embedded) {
        this._embedded = _embedded;
    }
}
