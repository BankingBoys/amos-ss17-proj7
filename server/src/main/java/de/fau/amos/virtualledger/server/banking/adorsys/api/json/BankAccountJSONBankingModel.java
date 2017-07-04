package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

public class BankAccountJSONBankingModel {

    private EmbeddedBankAccountJSONBankingModel embedded;

    public BankAccountJSONBankingModel() {
    }

    public EmbeddedBankAccountJSONBankingModel getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedBankAccountJSONBankingModel embedded) {
        this.embedded = embedded;
    }
}
