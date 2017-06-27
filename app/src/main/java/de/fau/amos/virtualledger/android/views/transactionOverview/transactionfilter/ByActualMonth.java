package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class ByActualMonth implements Filter<Transaction> {
    @Override
    public boolean shouldBeRemoved(Transaction t) {
        Calendar calTransaction = Calendar.getInstance();
        calTransaction.setTime(t.booking().getDate());

        Calendar calToday = new GregorianCalendar();
        if (calTransaction.get(Calendar.MONTH) != calToday.get(Calendar.MONTH)) {
            return true;
        }
        if (calTransaction.get(Calendar.YEAR) != calToday.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }
}
