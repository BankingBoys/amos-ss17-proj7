package de.fau.amos.virtualledger.dtos;

import java.util.List;

public class BankAccountBookings {

    private String bankaccessid;
    private String bankaccountid;
    private List<Booking> bookings;

    public BankAccountBookings(String bankaccessid, String bankaccountid, List<Booking> bookings) {
        this.bankaccessid = bankaccessid;
        this.bankaccountid = bankaccountid;
        this.bookings = bookings;
    }

    public BankAccountBookings() {

    }

    public String getBankaccessid() {
        return bankaccessid;
    }

    public void setBankaccessid(String bankaccessid) {
        this.bankaccessid = bankaccessid;
    }

    public String getBankaccountid() {
        return bankaccountid;
    }

    public void setBankaccountid(String bankaccountid) {
        this.bankaccountid = bankaccountid;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
