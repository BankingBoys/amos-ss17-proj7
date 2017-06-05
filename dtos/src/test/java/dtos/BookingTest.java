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

    private Date date = new Date(19930501);
    private double amount = 13123.3;
    Booking booking;

    /**
     *
     */
    @Before
    public void setUp() {
        booking = new Booking(date, amount);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        Assert.assertNotNull(booking);
    }

    /**
     *
     */
    @Test
    public void setAndGetDateTest() {
        Date newDate = new Date(20170505);
        booking.setDate(newDate);
        Assert.assertEquals(newDate, booking.getDate());
    }

    /**
     *
     */
    @Test
    public void setAndGetAmountTest() {
        double newAmount = 5555;
        booking.setAmount(newAmount);
        Assert.assertEquals(newAmount, booking.getAmount(), 0);
    }

}
