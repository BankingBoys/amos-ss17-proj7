package de.fau.amos.virtualledger.dtos;

public class BankAccountIdentifier {

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
}
