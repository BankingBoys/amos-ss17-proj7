package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account_identifiers")
public class BankAccountIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String accessid;
    private String accountid;

    public BankAccountIdentifier(String accessid, String accountid) {
        this.accessid = accessid;
        this.accountid = accountid;
    }

    public BankAccountIdentifier() {
    }

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
