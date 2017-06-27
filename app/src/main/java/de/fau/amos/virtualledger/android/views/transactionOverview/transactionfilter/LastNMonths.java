package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class LastNMonths implements Filter<Transaction> {
    private int numberOfMonths;
    public LastNMonths(int numberOfMonths){
        this.numberOfMonths = numberOfMonths;
    }

    @Override
    public boolean shouldBeRemoved(Transaction t) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(t.booking().getDate());

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new Date());

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return Math.abs(diffMonth) > this.numberOfMonths;
    }
}
