package de.fau.amos.virtualledger.dtos;

public class Booking {

    private int date;
    private double amount;

    public Booking(int date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Booking() {

    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
