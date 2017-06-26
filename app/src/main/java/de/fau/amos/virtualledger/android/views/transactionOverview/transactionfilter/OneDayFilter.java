package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class OneDayFilter implements Filter<Transaction> {
    private Date day;

    public OneDayFilter(Date day) {
        this.day = getZeroTimeDate(day);
    }

    @Override
    public boolean shouldBeRemoved(Transaction t) {
        return getZeroTimeDate(t.booking().getDate()).compareTo(this.day) != 0;
    }

    private Date getZeroTimeDate(Date input) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(input);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }
}
