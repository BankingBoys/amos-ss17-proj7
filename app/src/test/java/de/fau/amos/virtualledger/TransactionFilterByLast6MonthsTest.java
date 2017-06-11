package de.fau.amos.virtualledger;

import org.junit.Test;

import java.util.Date;

import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;
import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Last6Months;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionFilterByLast6MonthsTest {

    @Test
    public void teste_filter_withToday_shouldReturnStay() throws InterruptedException {
        Booking booking = new Booking();
        booking.setDate(new Date());
        Transaction transaction = new Transaction("TestBank", booking);
        Last6Months component_under_test = new Last6Months();

        assertThat(component_under_test.shouldBeRemoved(transaction)).isFalse();
    }

    @Test
    public void teste_filter_withTooOldDate_shouldReturnLeave() throws InterruptedException {
        Booking booking = new Booking();
        Date date = new Date();
        date.setYear(2001);
        booking.setDate(date);
        Transaction transaction = new Transaction("TestBank", booking);
        Last6Months component_under_test = new Last6Months();

        assertThat(component_under_test.shouldBeRemoved(transaction)).isTrue();
    }
}
