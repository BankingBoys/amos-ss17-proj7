package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class Last6Months implements Filter<Transaction> {
    @Override
    public boolean shouldBeRemoved(Transaction t) {
        return new LastNMonths(6).shouldBeRemoved(t);
    }
}
