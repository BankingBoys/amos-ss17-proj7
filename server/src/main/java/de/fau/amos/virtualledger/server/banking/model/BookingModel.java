package de.fau.amos.virtualledger.server.banking.model;

public class BookingModel {

    private int bookingDate = 0;
    private double amount = 0;

    public BookingModel() {
    }

    public int getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(int bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
