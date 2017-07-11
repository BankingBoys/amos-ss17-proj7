package de.fau.amos.virtualledger.android.api.sync;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sebastian on 09.07.17.
 */

public interface DataProvider<T> {

    Observable<List<T>> get();

    Observable<String> add(T newItem);
}
