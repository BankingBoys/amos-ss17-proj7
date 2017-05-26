package de.fau.amos.virtualledger.dtos;

/**
 * Created by Georg on 26.05.2017.
 */
public class BankAccountSync {

    private String bankaccessid;
    private String bankaccountid;
    private String pin;

    public BankAccountSync(String bankaccessid, String bankaccountid, String pin) {
        this.bankaccessid = bankaccessid;
        this.bankaccountid = bankaccountid;
        this.pin = pin;
    }

    public BankAccountSync() {
    }

    public String getBankaccessid() {

        return bankaccessid;
    }

    public void setBankaccessid(String bankaccessid) {
        this.bankaccessid = bankaccessid;
    }

    public String getBankaccountid() {
        return bankaccountid;
    }

    public void setBankaccountid(String bankaccountid) {
        this.bankaccountid = bankaccountid;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
