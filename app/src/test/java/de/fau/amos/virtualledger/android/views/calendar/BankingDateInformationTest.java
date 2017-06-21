package de.fau.amos.virtualledger.android.views.calendar;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BankingDateInformationTest {


    @Test
    public void constructor_and_getters_match() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;
        List<Booking> bookingList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, transactionList);

        //ASSERT
        assertThat(bankingDateInformation.getDateTime()).isEqualTo(dateTime);
        assertThat(bankingDateInformation.getAmount()).isEqualTo(amount);
        assertThat(bankingDateInformation.getBookingList()).isEmpty();
        assertThat(bankingDateInformation.getTransactions()).isEmpty();
    }


    @Test
    public void getAmountDelta_noBookings() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, new ArrayList<Transaction>());

        //ASSERT
        assertThat(bankingDateInformation.getAmountDelta()).isZero();
    }


    @Test
    public void getAmountDelta_bookings() {
        // SETUP
        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        double amount = 123.456;
        List<Transaction> transactionList = new ArrayList<>();
        double amount1 = 500.00;
        double amount2 = -100.00;

        Booking booking1 = new Booking(new Date(), amount1);
        Transaction transaction1 = new Transaction("some name","some id",booking1);
        transactionList.add(transaction1);

        Booking booking2 = new Booking(new Date(), amount2);
        Transaction transaction2 = new Transaction("some name","some id",booking2);
        transactionList.add(transaction2);

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(dateTime, amount, transactionList);

        //ASSERT
        assertThat(bankingDateInformation.getAmountDelta()).isEqualTo(amount1+amount2);
    }
}
