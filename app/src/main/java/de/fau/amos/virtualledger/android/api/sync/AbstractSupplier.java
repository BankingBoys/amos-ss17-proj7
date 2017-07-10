package de.fau.amos.virtualledger.android.api.sync;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier;

/**
 * Created by sebastian on 10.07.17.
 */

public abstract class AbstractSupplier<T> implements Observer, Supplier<T> {

    private Set<DataListening> dataListenings = new HashSet<>();

    private List<T> itemList = new ArrayList<>();

    @Override
    public List<T> getAll() {
        return new LinkedList<>(this.itemList);
    }

    @Override
    public void onResume() {
        this.logger().log(Level.INFO, "On Resume");
        dataManager().addObserver(this);
        this.logger().log(Level.INFO, "Syncstatus: " + this.dataManager().getSyncStatus());
        switch (dataManager().getSyncStatus()) {
            case NOT_SYNCED:
                this.dataManager().sync();
                break;
            case SYNCED:
                onSavingsUpdated();
                break;
        }
    }


    private void onSavingsUpdated() {
        this.logger().info("Data loading finished");
        this.itemList.clear();
        List<T> savingAccounts = null;
        try {
            savingAccounts = this.dataManager().getAll();
        } catch (SyncFailedException e) {
            Log.e("", "Sync failed");
            return;
        }
        if (savingAccounts != null) {
            this.itemList.addAll(savingAccounts);
        }
        notifyObservers();
    }

    @Override
    public void addDataListeningObject(DataListening observer) {
        if (this.dataListenings.isEmpty()) {
            this.dataManager().addObserver(this);
        }
        this.dataListenings.add(observer);
    }

    @Override
    public void deregister(DataListening observer) {
        this.dataListenings.remove(observer);
        if (this.dataListenings.isEmpty()) {
            this.dataManager().deleteObserver(this);
        }
    }

    @Override
    public void onPause() {
        this.logger().log(Level.INFO, "De-Registering from data manager");
        this.dataManager().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.onSavingsUpdated();
    }

    private void notifyObservers() {
        this.logger().info("Notify " + this.dataListenings.size() + " DataListening-Objects with " + this.itemList.size() + " items");
        for (DataListening dataListening : this.dataListenings) {
            dataListening.notifyDataChanged();
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }

    protected abstract DataManager<T> dataManager();
}
