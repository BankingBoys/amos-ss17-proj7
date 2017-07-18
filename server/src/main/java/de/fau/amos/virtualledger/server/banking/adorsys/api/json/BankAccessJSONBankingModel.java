package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

import com.owlike.genson.annotation.JsonProperty;

public class BankAccessJSONBankingModel {

    @JsonProperty("_embedded")
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
