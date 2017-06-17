package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionFilterByLast4WeeksTest {

    @Test
    public void teste_filter_withToday_shouldReturnStay() throws InterruptedException {
        Booking booking = new Booking();
        booking.setDate(new Date());
        Transaction transaction = new Transaction("TestBank", booking);
        Last4Weeks  component_under_test = new Last4Weeks();

        assertThat(component_under_test.shouldBeRemoved(transaction)).isFalse();
    }

    @Test
    public void teste_getWeeksDifference_with1Week_shouldReturnOneWeek() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse("11/06/2017");
        Date end = sdf.parse("04/06/2017");

        assertThat(Last4Weeks.getWeeksBetween(start,end)).isEqualTo(1);
    }
}
