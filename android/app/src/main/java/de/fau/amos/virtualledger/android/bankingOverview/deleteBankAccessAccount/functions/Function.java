package de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions;

/**
 * Created by sebastian on 21.05.17.
 */

public interface Function<T,R> {
    R apply(T item);
}
