package de.fau.amos.virtualledger.android.api.sync;

import java.util.List;
import java.util.Observer;

import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;

/**
 * Created by sebastian on 09.07.17.
 */

public interface DataManager<T> {

    /**
     * Syncs all Items with server.
     * Therefore the syncStatus goes firs into SYNC_IN_PROGRESS and back into SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    void sync();

    /**
     * gets the status of the BankingDataManager.
     * NOT_SYNCED if no sync was done yet.
     * SYNC_IN_PROGRESS if the sync is in progress yet.
     * SYNCED if a sync was done.
     */
    SyncStatus getSyncStatus();


    List<T> getAll() throws SyncFailedException;

    void add(T newItem, ServerCallStatusHandler h);

    void addObserver(Observer o);

    void deleteObserver(Observer o);
}
