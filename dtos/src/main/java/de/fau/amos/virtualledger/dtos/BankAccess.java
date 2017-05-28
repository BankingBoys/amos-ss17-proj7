package de.fau.amos.virtualledger.dtos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccess {

    private String id;
    private String name;
    private List<BankAccount> bankaccounts = new ArrayList<BankAccount>();

    /**
     *
     * @methodtype constructor
     */
    public BankAccess() { }

    /**
     *
     * @param id
     * @param name
     * @methodtype constructor
     */
    public BankAccess(String id, String name,List<BankAccount> bankaccounts ) {
        this(id, name);
        this.bankaccounts = bankaccounts;
    }

    public BankAccess(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return id
     * @methodtype getter
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * @methodtype setter
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return bank access name
     * @methodtype getter
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * @methodtype setter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return balance
     * @methodtype getter
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
     * @return List with Bank Accounts of this access
     * @methodtype getter
     */
    public List<BankAccount> getBankaccounts() {
        return bankaccounts;
    }

    /**
     *
     * @param bankaccounts
     * @methodtype setter
     */
    public void setBankaccounts(List<BankAccount> bankaccounts) {
        this.bankaccounts = bankaccounts;
    }

    /**
     *
     */
    public static Comparator<BankAccess> sortBankAccessByName = new Comparator<BankAccess>(){
        public int compare(BankAccess access1, BankAccess access2) {
            String access1Name = access1.getName().toUpperCase();
            String access2Name = access2.getName().toUpperCase();
            return access1Name.compareTo(access2Name);
        }
    };
}
