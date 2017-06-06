package de.fau.amos.virtualledger.dtos;

import java.util.Comparator;
import java.util.Locale;

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

    /**
     *
     */
    public static Comparator<BankAccount> sortBankAccountByName = new Comparator<BankAccount>(){
        public int compare(BankAccount account1, BankAccount account2) {
            String account1Name = account1.getName().toUpperCase(Locale.GERMAN);
            String account2Name = account2.getName().toUpperCase(Locale.GERMAN);
            return account1Name.compareTo(account2Name);
        }
    };
}
