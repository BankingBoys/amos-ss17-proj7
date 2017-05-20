package de.fau.amos.virtualledger.server.banking.model;

/**
 * Created by Georg on 20.05.2017.
 */
public class BankAccountBalanceBankingModel {

    private double availableHbciBalance;
    private double creditHbciBalance;
    private double readyHbciBalance;
    private double unreadyHbciBalance;
    private double usedHbciBalance;

    public BankAccountBalanceBankingModel() {
    }

    public double getAvailableHbciBalance() {
        return availableHbciBalance;
    }

    public void setAvailableHbciBalance(double availableHbciBalance) {
        this.availableHbciBalance = availableHbciBalance;
    }

    public double getCreditHbciBalance() {
        return creditHbciBalance;
    }

    public void setCreditHbciBalance(double creditHbciBalance) {
        this.creditHbciBalance = creditHbciBalance;
    }

    public double getReadyHbciBalance() {
        return readyHbciBalance;
    }

    public void setReadyHbciBalance(double readyHbciBalance) {
        this.readyHbciBalance = readyHbciBalance;
    }

    public double getUnreadyHbciBalance() {
        return unreadyHbciBalance;
    }

    public void setUnreadyHbciBalance(double unreadyHbciBalance) {
        this.unreadyHbciBalance = unreadyHbciBalance;
    }

    public double getUsedHbciBalance() {
        return usedHbciBalance;
    }

    public void setUsedHbciBalance(double usedHbciBalance) {
        this.usedHbciBalance = usedHbciBalance;
    }
}
