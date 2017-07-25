package de.fau.amos.virtualledger.server.banking.model;

import java.util.List;

public class BookingModel {

    private List<Integer> bookingDate;
    private Double amount;
    private String usage;

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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
