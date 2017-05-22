package de.fau.amos.virtualledger.android.functions;

/**
 * Created by Georg on 22.05.2017.
 */

public interface BiConsumer<T, R> {

    void accept(T item1, R item2);
}
