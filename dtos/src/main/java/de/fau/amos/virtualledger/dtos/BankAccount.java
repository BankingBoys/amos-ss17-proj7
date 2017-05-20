package de.fau.amos.virtualledger.dtos;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccount {

    private int bankId;
    private String bankAccountName;
    private double balance;

    /**
     *
     * @param bankId
     * @param bankAccountName
     * @param balance
     * @methodtype constructor
     */
    public BankAccount(int bankId, String bankAccountName, double balance) {
        this.bankId = bankId;
        this.bankAccountName = bankAccountName;
        this.balance = balance;
    }

    /**
     *
     * @return bankId
     * @methodtype getter
     */
    public int getBankId() {
        return bankId;
    }

    /**
     *
     * @param bankId
     * @methodtype setter
     */
    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    /**
     *
     * @return name of bank account
     * @methodtype getter
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     *
     * @param bankAccountName
     * @methodtype setter
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     *
     * @return balance
     * @methodtype getter
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     * @methodtype setter
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
