package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sebastian on 18.06.17.
 */

public class StubbedTransactionAdapter extends TransactionAdapter {
    private List<Transaction> presentedTransactions = new ArrayList<>();

    public StubbedTransactionAdapter() {
        super(null, 0, null);
    }

    @Override
    public void add(@Nullable Transaction object) {
        this.presentedTransactions.add(object);
    }

    public List<Transaction> presentedTransactions() {
        return this.presentedTransactions;
    }

    @Override
    public void clear() {
        this.presentedTransactions.clear();
    }

    @Override
    public String toString() {
        return "Manual Stub{" + this.hashCode() + "}";
    }

    @Override
    public void sort(@NonNull Comparator<? super Transaction> comparator) {
        //do nothing
    }

    @Override
    public void notifyDataSetChanged() {
        //do nothing
    }
}
