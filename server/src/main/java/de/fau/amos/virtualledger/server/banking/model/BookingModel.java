package de.fau.amos.virtualledger.server.banking.model;

import java.util.List;

public class BookingModel {

    private List<Integer> bookingDate;
    private Double amount;

    public BookingModel() {
    }

    public List<Integer> getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(List<Integer> bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
