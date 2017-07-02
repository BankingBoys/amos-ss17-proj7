package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

public class BankAccessJSONBankingModel {

    private EmbeddedBankAccessJSONBankingModel embedded;

    public BankAccessJSONBankingModel() {
    }

    public EmbeddedBankAccessJSONBankingModel getEmbedded() {
        return this.embedded;
    }

    public void setEmbedded(EmbeddedBankAccessJSONBankingModel embedded) {
        this.embedded = embedded;
    }
}
