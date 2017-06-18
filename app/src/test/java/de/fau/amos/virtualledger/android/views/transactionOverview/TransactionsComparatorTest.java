package de.fau.amos.virtualledger.android.views.transactionOverview;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.android.views.shared.transactionList.TransactionsComparator;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsComparatorTest {

    @Test
    public void teste_withTwoEqualDates_shouldReturnEquality() throws Exception {
        TransactionsComparator component_under_test = new TransactionsComparator();
        Booking booking = new Booking();
        booking.setDate(new Date());
        Transaction transaction = new Transaction("some bank", "some bank id", booking);

        assertThat(component_under_test.compare(transaction, transaction)).isEqualTo(0);
    }

    @Test
    public void teste_withLaterDate_shouldReturnGreater() throws Exception {
        TransactionsComparator component_under_test = new TransactionsComparator();
        Booking booking = new Booking();
        booking.setDate(toDate("01/01/17"));
        Transaction transaction = new Transaction("some bank", "some bank id", booking);

        booking = new Booking();
        booking.setDate(toDate("01/01/18"));
        Transaction later = new Transaction("some bank", "some bank id", booking);

        assertThat(component_under_test.compare(transaction, later)).isEqualTo(1);
    }

    @Test
    public void teste_withEarlierDate_shouldReturnSmaller() throws Exception {
        TransactionsComparator component_under_test = new TransactionsComparator();
        Booking booking = new Booking();
        booking.setDate(toDate("01/01/17"));
        Transaction transaction = new Transaction("some bank", "some bank id", booking);

        booking = new Booking();
        booking.setDate(toDate("01/01/16"));
        Transaction earlier = new Transaction("some bank", "some bank id", booking);

        assertThat(component_under_test.compare(transaction, earlier)).isEqualTo(-1);
    }

    private Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(date);
    }
}
