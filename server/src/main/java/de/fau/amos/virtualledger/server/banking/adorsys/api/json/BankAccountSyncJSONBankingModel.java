package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

import com.owlike.genson.annotation.JsonProperty;

public class BankAccountSyncJSONBankingModel {

    @JsonProperty("_embedded")
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
