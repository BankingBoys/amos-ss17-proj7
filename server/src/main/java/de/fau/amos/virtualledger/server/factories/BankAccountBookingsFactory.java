package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component

public class BankAccountBookingsFactory {

    public BankAccountBookings createBankAccountBookings(final List<BookingModel> bookingModels, final String bankAccessId, final String bankAccountId) {
        final List<Booking> resultBookings = new ArrayList<>();
        final BankAccountBookings result = new BankAccountBookings(bankAccessId, bankAccountId, resultBookings);
        for (final BookingModel bookingModel : bookingModels) {
            final List<Integer> dateList = bookingModel.getBookingDate();
            final Calendar calendar = Calendar.getInstance();
            calendar.set(dateList.get(0), dateList.get(1) - 1, dateList.get(2));
            resultBookings.add(new Booking(calendar.getTime(), bookingModel.getAmount()));
        }

        return result;
    }

}
