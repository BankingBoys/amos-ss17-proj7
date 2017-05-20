package de.fau.amos.virtualledger.server.banking.model;

/**
 * Created by Georg on 20.05.2017.
 */
public class BankAccessBankingModel {

    private String bankName;
    private String bankLogin;;
    private String bankCode;
    private String passportState;
    private String id;
    private String userId;
    private String pin;

    public BankAccessBankingModel() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogin() {
        return bankLogin;
    }

    public void setBankLogin(String bankLogin) {
        this.bankLogin = bankLogin;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPassportState() {
        return passportState;
    }

    public void setPassportState(String passportState) {
        this.passportState = passportState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
