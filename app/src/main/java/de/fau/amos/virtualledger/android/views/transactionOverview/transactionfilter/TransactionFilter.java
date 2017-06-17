package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public interface TransactionFilter {

    boolean shouldBeRemoved(Transaction t);
}
