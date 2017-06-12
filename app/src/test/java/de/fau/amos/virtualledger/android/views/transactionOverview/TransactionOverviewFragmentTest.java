package de.fau.amos.virtualledger.android.views.transactionOverview;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionOverviewFragmentTest {

    @Test
    public void teste_filterTransactionInitial_shouldOnlyPresentTransactionsOfActualMonth() throws Exception {
        TransactionOverviewFragment component_under_test = new MocketTransactionOverview();
        Transaction oldTransaction = new Transaction("some bank", oldBooking());
        component_under_test.allTransactions.add(oldTransaction);

        Transaction newTransaction = new Transaction("some new bank", newBooking());
        component_under_test.allTransactions.add(newTransaction);

        component_under_test.adapter = mockedAdapter();


        component_under_test.showUpdatedTransactions();

        assertThat(component_under_test.presentedTransactions).containsOnly(newTransaction);
    }


    @Test
    public void teste_filterTransaction12Months_shouldPresentAllTransactionsOfLast12Months() throws Exception {
        MocketTransactionOverview component_under_test = new MocketTransactionOverview();
        Transaction lastYearTransaction = new Transaction("some bank", lastYearBooking());
        component_under_test.allTransactions.add(lastYearTransaction);

        Transaction newTransaction = new Transaction("some new bank", newBooking());
        component_under_test.allTransactions.add(newTransaction);

        component_under_test.adapter = mockedAdapter();


        component_under_test.showUpdatedTransactions();
        component_under_test.filterTransactions("Last 12 months",null,null);

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
class MocketTransactionOverview extends TransactionOverviewFragment{
    public String toString(){
        return "test";
    }
}
