package de.fau.amos.virtualledger.android.views.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

public class BankingDateInformationTest {


    @Test
    public void constructor_and_getters_match() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;
        List<Booking> bookingList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, bookingList, transactionList);

        //ASSERT
        Assert.assertEquals(dateTime, bankingDateInformation.getDateTime());
        Assert.assertTrue(amount == bankingDateInformation.getAmount());
        Assert.assertEquals(bookingList, bankingDateInformation.getBookingList());
        Assert.assertEquals(transactionList, bankingDateInformation.getTransactions());
    }


    @Test
    public void getAmountDelta_noBookings() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;
        List<Booking> bookingList = new ArrayList<>();

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, bookingList);

        //ASSERT
        Assert.assertTrue(bankingDateInformation.getAmountDelta() == 0.0);
    }


    @Test
    public void getAmountDelta_bookings() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;
        List<Booking> bookingList = new ArrayList<>();
        double amount1 = 500.00;
        double amount2 = -100.00;

        Booking booking1 = new Booking(new Date(), amount1);
        bookingList.add(booking1);
        Booking booking2 = new Booking(new Date(), amount2);
        bookingList.add(booking2);

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, bookingList);

        //ASSERT
        Assert.assertTrue(bankingDateInformation.getAmountDelta() == amount1 + amount2);
    }
}
