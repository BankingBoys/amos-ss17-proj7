package de.fau.amos.virtualledger.android.views.transactionOverview;

/**
 * Created by sebastian on 11.06.17.
 */

public interface TransactionFilter {

    boolean shouldBeRemoved(Transaction t);
}
