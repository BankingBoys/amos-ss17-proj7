package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

public class BankAccountSyncJSONBankingModel {

    private EmbeddedBankAccountSyncJSONBankingModel embedded;

    public BankAccountSyncJSONBankingModel() {
    }

    public EmbeddedBankAccountSyncJSONBankingModel getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedBankAccountSyncJSONBankingModel embedded) {
        this.embedded = embedded;
    }
}
