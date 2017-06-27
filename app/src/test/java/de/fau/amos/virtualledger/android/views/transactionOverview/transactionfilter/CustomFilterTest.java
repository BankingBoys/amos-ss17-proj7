package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomFilterTest {

    @Test
    public void teste_filter_withEndDate_shouldReturnStay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse("11/06/2017");
        Date end = sdf.parse("11/07/2017");
        Booking booking = new Booking();
        booking.setDate(end);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new CustomFilter(start, end);


        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isFalse();
    }

    @Test
    public void teste_filter_withStartDate_shouldReturnStay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse("11/06/2017");
        Date end = sdf.parse("11/07/2017");
        Booking booking = new Booking();
        booking.setDate(start);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new CustomFilter(start, end);

        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isFalse();
    }

    @Test
    public void teste_filter_withDateBeforeStartDate_shouldReturnLeave() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse("11/06/2017");
        Date end = sdf.parse("11/07/2017");
        Date testDate = sdf.parse("11/04/2017");
        Booking booking = new Booking();
        booking.setDate(testDate);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new CustomFilter(start, end);

        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isTrue();
    }

    @Test
    public void teste_filter_withDateAfterEndDate_shouldReturnLeave() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse("11/06/2017");
        Date end = sdf.parse("11/07/2017");
        Date testDate = sdf.parse("11/08/2017");
        Booking booking = new Booking();
        booking.setDate(testDate);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new CustomFilter(start, end);

        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isTrue();
    }


}
