package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;
import de.fau.amos.virtualledger.android.views.transactionOverview.TransactionFilter;

/**
 * Created by sebastian on 11.06.17.
 */

public class Last12Months implements TransactionFilter{
    @Override
    public boolean shouldBeRemoved(Transaction t) {
        return new LastNMonths(12).shouldBeRemoved(t);
    }
}
