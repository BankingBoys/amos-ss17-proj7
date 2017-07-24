package de.fau.amos.virtualledger.android.api.sync;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.api.shared.RetrofitMessagedException;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
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


    public AbstractDataManager(DataProvider<T> dataProvider) {
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
        Consumer<List<T>> onNext = new Consumer<List<T>>() {
            @Override
            public void accept(@NonNull final List<T> itemList) throws Exception {
                AbstractDataManager.this.allCachedItems.addAll(itemList);
                onSyncComplete();
            }
        };
        subscribe(new DoNothing(), onNext, dataProvider.get());
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
    public void add(final T element, final ServerCallStatusHandler handler) {
        logger().info("Adding element: " + element);
        Consumer<StringApiModel> onNext = new Consumer<StringApiModel>() {
            @Override
            public void accept(@NonNull final StringApiModel s) throws Exception {
                handler.onOk();
                AbstractDataManager.this.sync();
            }
        };
        subscribe(handler, onNext, dataProvider.add(element));
    }

    @Override
    public void delete(final T element, final ServerCallStatusHandler handler) {
        logger().info("Adding element: " + element);
        Consumer<StringApiModel> onNext = new Consumer<StringApiModel>() {
            @Override
            public void accept(@NonNull final StringApiModel string) throws Exception {
                handler.onOk();
                logger().info("Trigger resync after succesful delete");
                AbstractDataManager.this.sync();
            }

            private Logger logger() {
                return Logger.getLogger(this.getClass().getCanonicalName());
            }
        };
        subscribe(handler, onNext, dataProvider.delete(element));
    }

    private <K> void subscribe(final ServerCallStatusHandler handler, Consumer<K> onNext, io.reactivex.Observable<K> add) {
        add.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed adding items", throwable);
                        if(throwable instanceof RetrofitMessagedException) {
                            RetrofitMessagedException messagedException = (RetrofitMessagedException) throwable;
                            Log.e(TAG, "Reason for failure; " + messagedException.getMessage());
                            if(handler instanceof Toaster) {
                                Toaster toaster = (Toaster) handler;
                                toaster.pushTechnicalErrorMessage(messagedException.getMessage());
                            }
                        }
                        handler.onTechnicalError();
                        AbstractDataManager.this.sync();
                    }
                });
    }
}

