package de.fau.amos.virtualledger.android.views.calendar;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.android.views.shared.transactionList.StubbedBankTransactionSupplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class BankingDateInformationTest {


    @Test
    public void constructor_and_getters_match() {
        // SETUP
        double amount = 123.456;
        List<Transaction> transactionList = new ArrayList<>();

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(amount, new StubbedBankTransactionSupplier());

        //ASSERT
        assertThat(bankingDateInformation.getAmount()).isEqualTo(amount);
        assertThat(bankingDateInformation.getTransactionSuppllier().getAll()).isEmpty();
    }


    @Test
    public void getAmountDelta_noBookings() {
        // SETUP
        double amount = 123.456;

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(amount, new StubbedBankTransactionSupplier());

        //ASSERT
        assertThat(bankingDateInformation.getAmountDelta()).isZero();
    }


    @Test
    public void getAmountDelta_bookings() {
        // SETUP
        double amount = 123.456;
        double amount1 = 500.00;
        double amount2 = -100.00;

        Booking booking1 = new Booking(new Date(), amount1);
        Transaction transaction1 = new Transaction("some name","some id",booking1);

        Booking booking2 = new Booking(new Date(), amount2);
        Transaction transaction2 = new Transaction("some name","some id",booking2);

        // ACT
        BankingDateInformation bankingDateInformation = new BankingDateInformation(amount, new StubbedBankTransactionSupplier(transaction1, transaction2));

        //ASSERT
        assertThat(bankingDateInformation.getAmountDelta()).isEqualTo(amount1+amount2);
    }
}
