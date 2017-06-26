package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Date;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class OneDayFilter implements Filter<Transaction> {
    private Date day;

    public OneDayFilter(Date day) {
        this.day = day;
    }

    @Override
    public boolean shouldBeRemoved(Transaction t) {
        if (this.day.equals(t.booking().getDate())) {
            return false;
        }
        return true;
    }
}
