package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sebastian on 17.06.17.
 */

public class StubbedBankTransactionSupplier implements Supplier<Transaction>{
    private List<Transaction> transactionsList = null;


    public StubbedBankTransactionSupplier(Transaction ... transactions){
        this.transactionsList = Arrays.asList(transactions);
    }

    public StubbedBankTransactionSupplier(){
        this.transactionsList = new ArrayList<>();
    }

    @Override
    public List<Transaction> getAll() {
        return this.transactionsList;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void addDataListeningObject(DataListening observer) {

    }

    @Override
    public void deregister(DataListening observer) {

    }

    @Override
    public void onPause() {

    }
}
