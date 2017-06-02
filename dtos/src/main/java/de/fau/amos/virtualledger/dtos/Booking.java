package de.fau.amos.virtualledger.dtos;

import java.util.Date;

public class Booking {

    private Date date;
    private double amount;

    public Booking(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Booking() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
