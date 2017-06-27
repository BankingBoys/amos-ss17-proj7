package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Filter;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Last12Months;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sebastian on 18.06.17.
 */

public class BankTransactionSupplierFilterTest {
    @Test
    public void teste_filterTransactionInitial_shouldOnlyPresentTransactionsOfActualMonth() throws Exception {
        //Arrange
        Transaction oldTransaction = new Transaction("some bank", "some bank number", oldBooking());
        Transaction newTransaction = new Transaction("some new bank", "some bank number", newBooking());
        Supplier<Transaction> component_under_test = new BankTransactionSuplierFilter(new StubbedBankTransactionSupplier(oldTransaction, newTransaction), new Last12Months());


        //Act
        List<Transaction> result = component_under_test.getAll();

        //Assert
        assertThat(result).containsOnly(newTransaction);
    }


    private TransactionAdapter mockedAdapter() {
        TransactionAdapter mock = Mockito.mock(TransactionAdapter.class);
        Mockito.when(mock.toString()).thenReturn("Mocket! adapter!");
        return mock;
    }

    @NonNull
    private Booking oldBooking() throws Exception {
        Booking booking = new Booking();
        booking.setDate(toDate("11/06/2012"));
        return booking;
    }

    @NonNull
    private Booking lastYearBooking() throws Exception {
        Booking booking = new Booking();
        Date date = new Date();
        date.setYear(date.getYear() - 1);
        booking.setDate(date);
        return booking;
    }

    @NonNull
    private Booking newBooking() throws Exception {
        Booking booking = new Booking();
        booking.setDate(new Date());
        return booking;
    }

    private Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(date);
    }

}
