package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sebastian on 17.06.17.
 */

public class StubbedBankTransactionSupplier implements BankTransactionSupplier{
    private List<Transaction> transactionsList = null;


    StubbedBankTransactionSupplier(Transaction ... transactions){
        this.transactionsList = Arrays.asList(transactions);
    }



    @Override
    public List<Transaction> getAllTransactions() {
        return this.transactionsList;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void addDataListeningObject(DataListening observer) {

    }

    @Override
    public void onPause() {

    }
}
