package de.fau.amos.virtualledger.android.views.transactionOverview;

import java.util.Comparator;

/**
 * Created by sebastian on 05.06.17.
 */

public class TransactionsComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction transaction, Transaction t1) {
        return t1.booking().getDate().compareTo(transaction.booking().getDate());
    }
}
