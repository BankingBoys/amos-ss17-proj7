package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.List;

/**
 * Created by sebastian on 17.06.17.
 */

public interface BankTransactionSupplier {

    List<Transaction> getAllTransactions();

    void onResume();

    void addDataListeningObject(DataListening observer);


    void deregister(DataListening observer);

    void onPause();
}
