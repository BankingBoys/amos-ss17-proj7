package de.fau.amos.virtualledger.android.api.sync;

import java.util.List;

import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;

/**
 * Created by sebastian on 09.07.17.
 */

public interface DataProvider<T> {

    Observable<List<T>> get();

    Observable<StringApiModel> add(T newItem);

    Observable<Void> delete(T item);
}
