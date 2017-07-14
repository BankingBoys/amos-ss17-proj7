package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dummies_bank_acount_balance_banking_models")
public class DummyBankAccountBalanceBankingModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double availableHbciBalance;
    private Double creditHbciBalance;
    private Double readyHbciBalance;
    private Double unreadyHbciBalance;
    private Double usedHbciBalance;

    public DummyBankAccountBalanceBankingModelEntity() {
    }

    public DummyBankAccountBalanceBankingModelEntity(BankAccountBalanceBankingModel bankAccountBalanceBankingModel) {
        availableHbciBalance = bankAccountBalanceBankingModel.getAvailableHbciBalance();
        creditHbciBalance = bankAccountBalanceBankingModel.getCreditHbciBalance();
        readyHbciBalance = bankAccountBalanceBankingModel.getReadyHbciBalance();
        unreadyHbciBalance = bankAccountBalanceBankingModel.getUnreadyHbciBalance();
        usedHbciBalance = bankAccountBalanceBankingModel.getUsedHbciBalance();
    }

    public Double getAvailableHbciBalance() {
        return availableHbciBalance;
    }

    public void setAvailableHbciBalance(Double availableHbciBalance) {
        this.availableHbciBalance = availableHbciBalance;
    }

    public Double getCreditHbciBalance() {
        return creditHbciBalance;
    }

    public void setCreditHbciBalance(Double creditHbciBalance) {
        this.creditHbciBalance = creditHbciBalance;
    }

    public Double getReadyHbciBalance() {
        return readyHbciBalance;
    }

    public void setReadyHbciBalance(Double readyHbciBalance) {
        this.readyHbciBalance = readyHbciBalance;
    }

    public Double getUnreadyHbciBalance() {
        return unreadyHbciBalance;
    }

    public void setUnreadyHbciBalance(Double unreadyHbciBalance) {
        this.unreadyHbciBalance = unreadyHbciBalance;
    }

    public Double getUsedHbciBalance() {
        return usedHbciBalance;
    }

    public void setUsedHbciBalance(Double usedHbciBalance) {
        this.usedHbciBalance = usedHbciBalance;
    }

    public BankAccountBalanceBankingModel transformToBankAccountBalanceBankingModel() {
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setAvailableHbciBalance(availableHbciBalance);
        bankAccountBalanceBankingModel.setCreditHbciBalance(creditHbciBalance);
        bankAccountBalanceBankingModel.setReadyHbciBalance(readyHbciBalance);
        bankAccountBalanceBankingModel.setUnreadyHbciBalance(unreadyHbciBalance);
        bankAccountBalanceBankingModel.setUsedHbciBalance(usedHbciBalance);
        return bankAccountBalanceBankingModel;
    }
}
