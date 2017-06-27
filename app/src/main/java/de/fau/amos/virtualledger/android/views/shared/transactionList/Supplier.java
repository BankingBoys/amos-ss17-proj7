package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.List;

/**
 * Created by sebastian on 17.06.17.
 */

public interface Supplier<T> {

    List<T> getAll();

    void onResume();

    void addDataListeningObject(DataListening observer);

    void deregister(DataListening observer);

    void onPause();
}
