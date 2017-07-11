package de.fau.amos.virtualledger.android.api.sync;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static de.fau.amos.virtualledger.android.data.SyncStatus.NOT_SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNC_IN_PROGRESS;


public abstract class AbstractDataManager<T> extends Observable implements DataManager<T> {

    private DataProvider<T> dataProvider;

    private final static String TAG = AbstractDataManager.class.getSimpleName();

    private List<T> allCachedItems = new LinkedList<>();

    //Set if sync failed and thrown in getters
    private SyncFailedException syncFailedException = null;

    private SyncStatus syncStatus = NOT_SYNCED;
    private AtomicInteger syncsActive = new AtomicInteger(0);


    public AbstractDataManager(DataProvider<T> dataProvider){
        this.dataProvider = dataProvider;
    }

    /**
     * Syncs all bankAccess and booking data with server.
     * Therefore the syncStatus goes firs into SYNC_IN_PROGRESS and back into SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    @Override
    public void sync() {
        this.allCachedItems = new LinkedList<>();
        syncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        syncsActive.addAndGet(1);
        dataProvider.get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<T>>() {
                    @Override
                    public void accept(@NonNull final List<T> itemList) throws Exception {
                        AbstractDataManager.this.allCachedItems.addAll(itemList);
                        onSyncComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed getting items", throwable);
                        syncFailedException = new SyncFailedException(throwable);
                        onSyncComplete();
                    }
                });
    }

    private void onSyncComplete() {
        final int syncsLeft = syncsActive.decrementAndGet();
        if (syncsLeft == 0) {
            syncStatus = SYNCED;
            setChanged();
            notifyObservers();
        }
    }


    @Override
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    @Override
    public List<T> getAll() throws SyncFailedException {
        if (syncFailedException != null) throw syncFailedException;
        if (syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        logger().info("Number items synct: " + this.allCachedItems.size());
        return new LinkedList<>(allCachedItems);
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.hashCode() + "}");
    }

    @Override
    public void add(final T savingsAccount) {
        dataProvider.add(savingsAccount)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        AbstractDataManager.this.sync();
                    }
                });
    }
}

