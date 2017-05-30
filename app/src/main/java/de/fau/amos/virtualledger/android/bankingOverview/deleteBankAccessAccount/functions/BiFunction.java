package de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions;

/**
 * Created by sebastian on 23.05.17.
 */

public interface BiFunction<T, R, S> {
    S apply(T t, R r);
}
