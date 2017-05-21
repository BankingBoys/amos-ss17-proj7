package de.fau.amos.virtualledger.server.model;

import javax.persistence.*;

/**
 * Created by ramimahfoud on 20/05/2017.
 */

@Entity
@Table(name = "BankAccount")
public class BankAccount {


    private String accountName;


    private int  accountAccessId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public BankAccount(){}


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public int getAccountAccessId() {
        return accountAccessId;
    }

    public void setAccountAccessId(int accountAccessId) {
        this.accountAccessId = accountAccessId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
