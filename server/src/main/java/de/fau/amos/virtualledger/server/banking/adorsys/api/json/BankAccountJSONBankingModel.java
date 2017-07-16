package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

import com.owlike.genson.annotation.JsonProperty;

public class BankAccountJSONBankingModel {

    @JsonProperty("_embedded")
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
