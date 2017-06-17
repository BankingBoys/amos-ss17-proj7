package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Last12Months;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionOverviewFragmentTest {

    @Test
    public void teste_filterTransactionInitial_shouldOnlyPresentTransactionsOfActualMonth() throws Exception {
        //Arrange
        TransactionListFragment component_under_test = new MockedTransactionList();
        Transaction oldTransaction = new Transaction("some bank", oldBooking());
        Transaction newTransaction = new Transaction("some new bank", newBooking());
        component_under_test.adapter = mockedAdapter();


        //Act
        component_under_test.pushDataProvider(new StubbedBankTransactionSupplier(oldTransaction,newTransaction));
        component_under_test.showUpdatedTransactions();

        //Assert
        assertThat(component_under_test.presentedTransactions).containsOnly(newTransaction);
    }


    @Test
    public void teste_filterTransaction12Months_shouldPresentAllTransactionsOfLast12Months() throws Exception {
        // Arrange
        TransactionListFragment component_under_test = new MockedTransactionList();
        Transaction lastYearTransaction = new Transaction("some bank", lastYearBooking());
        Transaction newTransaction = new Transaction("some new bank", newBooking());

        //Act
        component_under_test.pushDataProvider(new StubbedBankTransactionSupplier(newTransaction, lastYearTransaction));
        component_under_test.adapter = mockedAdapter();

        component_under_test.showUpdatedTransactions();
        component_under_test.changeFilterTo(new Last12Months());

        //Assert
        assertThat(component_under_test.presentedTransactions).containsOnly(newTransaction, lastYearTransaction);
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
class MockedTransactionList extends TransactionListFragment{
    public String toString(){
        return "test";
    }
}

