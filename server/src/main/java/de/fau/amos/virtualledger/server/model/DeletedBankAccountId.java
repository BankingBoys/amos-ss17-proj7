package de.fau.amos.virtualledger.server.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DeletedBankAccountId implements Serializable {

    private String bankAccessId;
    private String bankAccountId;

    public DeletedBankAccountId() {
    }

    public DeletedBankAccountId(String bankAccessId, String bankAccountId) {
        this.bankAccessId = bankAccessId;
        this.bankAccountId = bankAccountId;
    }

    public String getBankAccessId() {
        return bankAccessId;
    }

    public void setBankAccessId(String bankAccessId) {
        this.bankAccessId = bankAccessId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
