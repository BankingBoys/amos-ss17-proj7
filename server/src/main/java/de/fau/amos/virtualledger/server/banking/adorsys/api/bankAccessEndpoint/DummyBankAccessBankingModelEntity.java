package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dummies_bank_access_banking_models")
public class DummyBankAccessBankingModelEntity {

    private String bankName = null;
    private String bankLogin = null;
    private String bankCode = null;
    private String passportState = null;

    @Id
    private String id = null;

    private String userId = null;
    private String pin = null;

    public DummyBankAccessBankingModelEntity() {
    }
    public DummyBankAccessBankingModelEntity(BankAccessBankingModel bankAccessBankingModel) {
        bankName = bankAccessBankingModel.getBankName();
        bankLogin = bankAccessBankingModel.getBankLogin();
        bankCode = bankAccessBankingModel.getBankCode();
        passportState = bankAccessBankingModel.getPassportState();
        id = bankAccessBankingModel.getId();
        userId = bankAccessBankingModel.getUserId();
        pin = bankAccessBankingModel.getPin();
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

    public BankAccessBankingModel transformToBankAccessBankingModel() {
        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setBankName(bankName);
        bankAccessBankingModel.setBankLogin(bankLogin);
        bankAccessBankingModel.setBankCode(bankCode);
        bankAccessBankingModel.setPassportState(passportState);
        bankAccessBankingModel.setId(id);
        bankAccessBankingModel.setUserId(userId);
        bankAccessBankingModel.setPin(pin);
        return bankAccessBankingModel;
    }
}
