package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;
import de.fau.amos.virtualledger.android.views.transactionOverview.TransactionFilter;

/**
 * Created by sebastian on 11.06.17.
 */

public class Last12Months implements TransactionFilter{
    @Override
    public boolean shouldBeRemoved(Transaction t) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(t.booking().getDate());

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new Date());

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        System.out.println(diffMonth);
        return Math.abs(diffMonth) > 12;
    }
}
