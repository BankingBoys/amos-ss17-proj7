package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class Last4Weeks implements TransactionFilter {
    @Override
    public boolean shouldBeRemoved(Transaction t) {
        int weeks_difference = getWeeksBetween(t.booking().getDate(), new Date());
        return weeks_difference > 4;
    }


    protected static int getWeeksBetween(Date a, Date b) {

        if (b.before(a)) {
            return getWeeksBetween(b, a);
        }
        a = resetTime(a);
        b = resetTime(b);

        Calendar cal = new GregorianCalendar();
        cal.setTime(a);
        int weeks = 0;
        while (cal.getTime().before(b)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return Math.abs(weeks);
    }

    private static Date resetTime(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
