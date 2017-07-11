package de.fau.amos.virtualledger.android.api.sync;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;
import de.fau.amos.virtualledger.dtos.Contact;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sebastian on 10.07.17.
 */

public class AbstractSupplierTest {

    @BeforeClass
    public static void setUpRxSchedulers() {
        final Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(new Executor() {
                    @Override
                    public void execute(@android.support.annotation.NonNull Runnable runnable) {
                        runnable.run();
                    }
                });
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });
        RxJavaPlugins.setInitComputationSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });
        RxJavaPlugins.setInitNewThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });
        RxJavaPlugins.setInitSingleSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                                                        @Override
                                                        public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                                                            return immediate;
                                                        }
                                                    }
        );
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });
    }


    @Test
    public void teste_onResume_withNotSyncedDataManager_shouldCallSync_and_notDataListenings() {
        StubbedDataManager dataManager = new StubbedDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);

        componenet_under_test.onResume();

        assertThat(dataManager.syncCalled()).isEqualTo(1);
        assertThat(dataListening.timesCalled()).isZero();
    }

    @Test
    public void teste_onResume_withSyncedDataManager_shouldNotCallSync() {
        StubbedDataManager dataManager = new StubbedDataManager();
        dataManager.setStatus(SyncStatus.SYNCED);
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);

        componenet_under_test.onResume();

        assertThat(dataManager.syncCalled()).isZero();
    }


    @Test
    public void teste_onResume_withSyncedDataManager_shouldCallNotifyOnRegisteredObserversOnce() {
        StubbedDataManager dataManager = new StubbedDataManager();
        dataManager.setStatus(SyncStatus.SYNCED);
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);

        componenet_under_test.onResume();

        assertThat(dataListening.timesCalled()).isEqualTo(1);
    }

    @Test
    public void teste_onResume_withFailedSync_shouldNotCallDataListenings() {
        StubbedDataManager dataManager = new ExceptinoDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);

        componenet_under_test.onResume();

        assertThat(dataListening.timesCalled()).isZero();
    }



    @Test
    public void teste_onResume_withObserver_shouldObserveDataListing() {
        StubbedDataManager dataManager = new StubbedDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);

        componenet_under_test.onResume();

        assertThat(dataManager.observers()).containsExactly(componenet_under_test);
    }


    @Test
    public void teste_onPause_withObserver_shouldDeregisterItselfFromDataListening() {
        StubbedDataManager dataManager = new StubbedDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);

        componenet_under_test.onResume();
        componenet_under_test.onPause();

        assertThat(dataManager.observers()).isEmpty();
    }

    @Test
    public void teste_addDataListening_shouldRegisterItselfOnDataManager() {
        StubbedDataManager dataManager = new StubbedDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();
        componenet_under_test.addDataListeningObject(dataListening);


        assertThat(dataManager.observers()).containsExactly(componenet_under_test);
    }

    @Test
    public void teste_removeDataListening_withLastListening_shouldDeRegisterItselfOnDataManager() {
        StubbedDataManager dataManager = new StubbedDataManager();
        FullfilledSupplier componenet_under_test = new FullfilledSupplier(dataManager);
        DummyDataListening dataListening = new DummyDataListening();

        componenet_under_test.addDataListeningObject(dataListening);
        componenet_under_test.deregister(dataListening);

        assertThat(dataManager.observers()).isEmpty();
    }


}

class FullfilledSupplier extends AbstractSupplier<Contact> {

    private DataManager<Contact> dataManager;

    FullfilledSupplier(DataManager<Contact> dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    protected DataManager<Contact> dataManager() {
        return this.dataManager;
    }
}

class StubbedDataManager implements DataManager<Contact> {

    private int syncCalled = 0;

    private SyncStatus status = SyncStatus.NOT_SYNCED;

    private List<Contact> contacts;

    private Set<Observer> observers = new HashSet<>();

    StubbedDataManager(Contact... contacts) {
        this.contacts = new LinkedList<>(Arrays.asList(contacts));
    }

    void setStatus(SyncStatus status) {
        this.status = status;
    }

    @Override
    public void sync() {
        this.syncCalled++;
    }

    int syncCalled() {
        return this.syncCalled;
    }

    @Override
    public SyncStatus getSyncStatus() {
        return this.status;
    }

    @Override
    public List<Contact> getAll() throws SyncFailedException {
        return this.contacts;
    }

    @Override
    public void add(Contact newItem) {
        this.contacts.add(newItem);
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        this.observers.remove(o);
    }

    Set<Observer> observers(){
        return this.observers;
    }
}
class ExceptinoDataManager extends StubbedDataManager{

    @Override
    public List<Contact> getAll()throws SyncFailedException {
        throw new SyncFailedException();
    }
}

class DummyDataListening implements DataListening {
    private int called = 0;


    int timesCalled() {
        return this.called;
    }

    @Override
    public void notifyDataChanged() {
        this.called++;
    }
}
