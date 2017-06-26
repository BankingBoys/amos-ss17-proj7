package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

/**
 * Created by sebastian on 11.06.17.
 */

public interface Filter<T> {

    boolean shouldBeRemoved(T t);
}
