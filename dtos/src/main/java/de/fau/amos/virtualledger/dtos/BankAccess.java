package de.fau.amos.virtualledger.dtos;

import java.util.List;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccess {

    private int bankId;
    private String bankAccessName;
    private double balance;
    private List<BankAccount> bankAccounts;

    /**
     *
     * @param bankId
     * @param bankAccessName
     * @param balance
     * @methodtype constructor
     */
    public BankAccess(int bankId, String bankAccessName, double balance) {
        this.bankId = bankId;
        this.bankAccessName = bankAccessName;
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
     * @return bank access name
     * @methodtype getter
     */
    public String getBankAccessName() {
        return bankAccessName;
    }

    /**
     *
     * @param bankAccessName
     * @methodtype setter
     */
    public void setBankAccessName(String bankAccessName) {
        this.bankAccessName = bankAccessName;
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

    /**
     *
     * @return List with Bank Accounts of this access
     * @methodtype getter
     */
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     *
     * @param bankAccounts
     * @methodtype setter
     */
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
