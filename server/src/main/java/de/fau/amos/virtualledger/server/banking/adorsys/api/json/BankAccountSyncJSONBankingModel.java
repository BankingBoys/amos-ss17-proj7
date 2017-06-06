package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

public class BankAccountSyncJSONBankingModel {

    private EmbeddedBankAccountSyncJSONBankingModel _embedded;

    public BankAccountSyncJSONBankingModel() {
    }

    public EmbeddedBankAccountSyncJSONBankingModel get_embedded() {
        return _embedded;
    }

    public void set_embedded(EmbeddedBankAccountSyncJSONBankingModel _embedded) {
        this._embedded = _embedded;
    }
}
