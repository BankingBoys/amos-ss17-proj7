package de.fau.amos.virtualledger.server.banking.model;

public class BookingModel {

    private Long bookingDate;
    private Double amount;

    public BookingModel() {
    }

    public Long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
