package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;

import static org.assertj.core.api.Assertions.assertThat;

public class OneDayFilterTest {

    @Test
    public void teste_filter_withMatchingDay_shouldReturnStay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = sdf.parse("11/07/2017");
        Date bookingDate = sdf.parse("11/07/2017");
        Booking booking = new Booking();
        booking.setDate(bookingDate);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new OneDayFilter(expectedDate);


        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isFalse();
    }

    @Test
    public void teste_filter_withNotMatchingDay_shouldReturnLeave() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = sdf.parse("11/07/2017");
        Date bookingDate = sdf.parse("12/07/2017");
        Booking booking = new Booking();
        booking.setDate(bookingDate);
        Transaction testTransaction = new Transaction("someBank", "some bank id", booking);

        Filter component_under_test = new OneDayFilter(expectedDate);

        assertThat(component_under_test.shouldBeRemoved(testTransaction)).isTrue();
    }
}
