package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionOverviewFragmentTest {

    @Test
    public void teste_filterTransactionInitial_shouldPresentAllTransactionsOfTheSupplier() throws Exception {
        //Arrange
        TransactionListFragment component_under_test = new MockedTransactionListFragment();
        StubbedTransactionAdapter stubbedTransactionAdapter = new StubbedTransactionAdapter();
        component_under_test.adapter = stubbedTransactionAdapter;
        Transaction oldTransaction = new Transaction("some bank", "some bank number", oldBooking());
        Transaction newTransaction = new Transaction("some new bank", "some bank number", newBooking());


        //Act
        component_under_test.pushDataProvider(new StubbedBankTransactionSupplier(oldTransaction, newTransaction));
        component_under_test.showUpdatedTransactions();

        //Assert
        assertThat(stubbedTransactionAdapter.presentedTransactions()).containsOnly(newTransaction, oldTransaction);
    }


    @NonNull
    private Booking oldBooking() throws Exception {
        Booking booking = new Booking();
        booking.setDate(toDate("11/06/2012"));
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

class MockedTransactionListFragment extends TransactionListFragment {
    @Override
    public String toString() {
        return "Manual Mocked";
    }
}

