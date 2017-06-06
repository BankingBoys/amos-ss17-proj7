package de.fau.amos.virtualledger.android.transactionOverview;

import de.fau.amos.virtualledger.dtos.Booking;

/**
 * Created by sebastian on 05.06.17.
 */

public class Transaction {

    private String bankName;
    private Booking booking;


    public Transaction(String bankName, Booking booking){
        this.bankName = bankName;
        this.booking = booking;
    }

    public Booking booking(){
        return this.booking;
    }

    public String bankName(){
        return this.bankName;
    }
}
