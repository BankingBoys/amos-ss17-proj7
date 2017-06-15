package de.fau.amos.virtualledger.android.views.calendar;

import java.util.List;

import de.fau.amos.virtualledger.dtos.Booking;

/**
 * Created by Georg on 15.06.2017.
 */

public class BankingDateInformation {

    private double amount;
    private double amountDelta;
    private List<Booking> bookingList;

    public BankingDateInformation(double amount, double amountDelta, List<Booking> bookingList) {
        this.amount = amount;
        this.amountDelta = amountDelta;
        this.bookingList = bookingList;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountDelta() {
        return amountDelta;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }
}
