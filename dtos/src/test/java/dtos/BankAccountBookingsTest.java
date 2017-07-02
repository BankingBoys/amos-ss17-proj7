package dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;

/**
 * Created by Simon on 05.06.2017.
 */
public class BankAccountBookingsTest {
    private final String bankaccessid = "testId";
    private final String bankaccountid = "testId";
    private List<Booking> bookings;
    private BankAccountBookings accountBookings;
    private final int earlyDate = 1990;
    private final double lowAmount = 50.01;
    private final int lateDate = 1990;
    private final double highAmount = 100000.01;

    /**
     *
     */
    @Before
    public void setUp() {
        Booking booking1 = new Booking(new Date(earlyDate), lowAmount);
        Booking booking2 = new Booking(new Date(lateDate), highAmount);
        bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        accountBookings = new BankAccountBookings(bankaccessid, bankaccountid, bookings);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        BankAccountBookings accountBookings = new BankAccountBookings(bankaccessid, bankaccountid, bookings);
        Assert.assertNotNull(accountBookings);
    }

    /**
     *
     */
    @Test
    public void setAndGetBankAccessIdTest() {
        String newAccessId = "newAccessId";
        accountBookings.setBankaccessid(newAccessId);
        Assert.assertNotNull(newAccessId, accountBookings.getBankaccessid());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankAccountIdTest() {
        String newAccountId = "newAccountId";
        accountBookings.setBankaccountid(newAccountId);
        Assert.assertNotNull(newAccountId, accountBookings.getBankaccountid());
    }

    /**
     *
     */
    @Test
    public void getBookingListTest() {
        final double lowAmount = 30.01;
        final double highAmount = 50000.01;
        Booking booking1new = new Booking(lateDate(), lowAmount);
        Booking booking2new = new Booking(earlyDate(), highAmount);
        List<Booking> newBookingList = new ArrayList<>();
        newBookingList.add(booking1new);
        newBookingList.add(booking2new);
        BankAccountBookings accountBookings = new BankAccountBookings(bankaccessid, bankaccountid, newBookingList);
        Assert.assertEquals(newBookingList, accountBookings.getBookings());
    }

    private Date earlyDate() {
        final int earlyDate = 124124;
        return new Date(earlyDate);
    }

    private Date lateDate() {
        final int lateDate = 555666;
        return new Date(lateDate);
    }

}
