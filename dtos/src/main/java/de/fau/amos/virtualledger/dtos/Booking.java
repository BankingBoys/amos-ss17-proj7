package de.fau.amos.virtualledger.dtos;

import java.util.Date;

public class Booking {

    private Date date;
    private double amount;

    // TODO change when adorsys fixed their api
    private String usage = "Test Usage";

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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + date + " " + amount + "}";
    }
}
