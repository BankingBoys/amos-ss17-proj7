package de.fau.amos.virtualledger.dtos;

/**
 * Created by Georg on 22.05.2017.
 */
public class BankAccessCredential {

    private String bankcode;
    private String banklogin;
    private String pin;

    public BankAccessCredential() {
    }

    public BankAccessCredential(String bankcode, String banklogin, String pin) {
        this.bankcode = bankcode;
        this.banklogin = banklogin;
        this.pin = pin;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBanklogin() {
        return banklogin;
    }

    public void setBanklogin(String banklogin) {
        this.banklogin = banklogin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
