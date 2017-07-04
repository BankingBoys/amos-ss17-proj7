package dtos;

import de.fau.amos.virtualledger.dtos.Booking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Simon on 05.06.2017.
 */
public class BookingTest {

    private static final int LATE_DATE = 19930501;
    private final Date date = new Date(LATE_DATE);
    private static final double TEST_AMOUNT = 13123.3;
    private Booking booking;

    /**
     *
     */
    @Before
    public void setUp() {
        booking = new Booking(date, TEST_AMOUNT);
    }

    /**
     *
     */
    @Test
    public void setAndGetDateTest() {
        final int lateDate = 20170505;
        final Date newDate = new Date(lateDate);
        booking.setDate(newDate);
        Assert.assertEquals(newDate, booking.getDate());
    }

    /**
     *
     */
    @Test
    public void setAndGetAmountTest() {
        final double newAmount = 5555;
        booking.setAmount(newAmount);
        Assert.assertEquals(newAmount, booking.getAmount(), 0);
    }

    /**
     *
     */
    @Test
    public void setAndGetUsageTest() {
        final String newTestUsage = "TestUsage2";
        booking.setUsage(newTestUsage);
        final String testUsage = booking.getUsage();
        Assert.assertEquals(newTestUsage, testUsage);
    }

}
