package de.fau.amos.virtualledger.server.banking.model;

/**
 * Created by Georg on 20.05.2017.
 */
public class BankAccountBalanceBankingModel {

    private Double availableHbciBalance;
    private Double creditHbciBalance;
    private Double readyHbciBalance;
    private Double unreadyHbciBalance;
    private Double usedHbciBalance;

    public BankAccountBalanceBankingModel() {
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
}
