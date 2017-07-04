package de.fau.amos.virtualledger.dtos;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccount {

    private String bankid;
    private String name;
    private double balance;

    /**
     *
     */
    public BankAccount() {
    }

    /**
     *
     */
    public BankAccount(String bankid, String name, double balance) {
        this.bankid = bankid;
        this.name = name;
        this.balance = balance;
    }

    /**
     *
     */
    public String getBankid() {
        return bankid;
    }

    /**
     *
     */
    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
