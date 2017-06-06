package dtos;

import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Simon on 05.06.2017.
 */
public class BankAccountBookingsTest {
    private String bankaccessid="testId";
    private String bankaccountid="testId";
    private List<Booking> bookings;
    private BankAccountBookings accountBookings;

    /**
     *
     */
    @Before
    public void setUp() {
        Booking booking1 = new Booking(new Date(1234), 111.1);
        Booking booking2 = new Booking(new Date(5678), 111.1);
        bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        accountBookings = new BankAccountBookings(bankaccessid, bankaccountid,bookings);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        BankAccountBookings accountBookings = new BankAccountBookings(bankaccessid, bankaccountid,bookings);
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
        accountBookings.setBankaccountid(newAccountId );
        Assert.assertNotNull(newAccountId , accountBookings.getBankaccountid());
    }

    /**
     *
     */
    @Test
    public void getBookingListTest() {
        Booking booking1new = new Booking(new Date(555666), 1231.1);
        Booking booking2new = new Booking(new Date(124124), 11231.1);
        List<Booking> newBookingList = new ArrayList<>();
        newBookingList.add(booking1new);
        newBookingList.add(booking2new);
        BankAccountBookings accountBookings = new BankAccountBookings(bankaccessid, bankaccountid, newBookingList);
        Assert.assertEquals(newBookingList, accountBookings.getBookings());
    }



}
