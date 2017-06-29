package de.fau.amos.virtualledger.dtos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccess {

    private String id;
    private String name;
    private String bankcode;
    private String banklogin;
    private List<BankAccount> bankaccounts = new ArrayList<BankAccount>();

    /**
     *
     */
    public BankAccess() { }

    /**
     *
     */
    public BankAccess(String id, String name, String bankcode, String banklogin, List<BankAccount> bankaccounts ) {
        this(id, name, bankcode, banklogin);
        this.bankaccounts = bankaccounts;
    }

    /**
     *
     */
    public BankAccess(String id, String name, String bankcode, String banklogin) {
        this.id = id;
        this.name = name;
        this.bankcode = bankcode;
        this.banklogin = banklogin;
    }

    /**
     *
     */
    public String getId() {
        return id;
    }

    /**
     *
     */
    public void setId(String id) {
        this.id = id;
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
        double balance = 0;
        for (BankAccount bankAccount: this.bankaccounts) {
            balance += bankAccount.getBalance();
        }
        return balance;
    }

    /**
     *
     */
    public List<BankAccount> getBankaccounts() {
        return bankaccounts;
    }

    /**
     *
     */
    public void setBankaccounts(List<BankAccount> bankaccounts) {
        this.bankaccounts = bankaccounts;
    }

    /**
     *
     */
    public static Comparator<BankAccess> sortBankAccessByName = new Comparator<BankAccess>(){
        @Override
		public int compare(BankAccess access1, BankAccess access2) {
            String access1Name = access1.getName().toUpperCase(Locale.GERMAN);
            String access2Name = access2.getName().toUpperCase(Locale.GERMAN);
            return access1Name.compareTo(access2Name);
        }
    };

    /**
     *
     */
    public String getBankcode() {
        return bankcode;
    }

    /**
     *
     */
    public void setBankcode(final String bankcode) {
        this.bankcode = bankcode;
    }

    /**
     *
     */
    public String getBanklogin() {
        return banklogin;
    }

    /**
     *
     */
    public void setBanklogin(final String banklogin) {
        this.banklogin = banklogin;
    }
}
