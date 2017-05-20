package de.fau.amos.virtualledger.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccess {

    private String bankid;
    private String bankname;
    private List<BankAccount> bankaccounts = new ArrayList<BankAccount>();

    /**
     *
     * @methodtype constructor
     */
    public BankAccess() { }

    /**
     *
     * @param bankid
     * @param bankname
     * @methodtype constructor
     */
    public BankAccess(String bankid, String bankname) {
        this.bankid = bankid;
        this.bankname = bankname;
    }

    /**
     *
     * @return bankid
     * @methodtype getter
     */
    public String getBankid() {
        return bankid;
    }

    /**
     *
     * @param bankid
     * @methodtype setter
     */
    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    /**
     *
     * @return bank access name
     * @methodtype getter
     */
    public String getBankname() {
        return bankname;
    }

    /**
     *
     * @param bankname
     * @methodtype setter
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
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
}
