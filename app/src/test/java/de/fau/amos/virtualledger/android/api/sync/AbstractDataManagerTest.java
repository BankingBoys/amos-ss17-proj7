package de.fau.amos.virtualledger.android.api.sync;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sebastian on 10.07.17.
 */

public class AbstractDataManagerTest {
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
    public void test_initialState_isNotInSync(){
        FullfilledDataManager component_under_test = new FullfilledDataManager(new StubbedSavingsProvider());

        assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.NOT_SYNCED);
    }

    @Test
    public void test_stateWithStartetSync_shouldReturnInSync(){
        FullfilledDataManager component_under_test = new FullfilledDataManager(new StubbedSavingsProvider());

        component_under_test.sync();

        assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.SYNC_IN_PROGRESS);
    }

    @Test
    public void test_stateWithSyncCompleteCall_shouldReturnSyncComplete(){
        StubbedSavingsProvider dataProvider = new StubbedSavingsProvider();
        FullfilledDataManager component_under_test = new FullfilledDataManager(dataProvider);

        component_under_test.sync();
        dataProvider.fireOnNextEvent();

        assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.SYNCED);
    }



    @Test(expected = IllegalStateException.class)
    public void test_getAll_withStartetSync_shouldThrowIllegalStateException() throws Exception{
        FullfilledDataManager component_under_test = new FullfilledDataManager(new StubbedSavingsProvider());

        component_under_test.sync();
        component_under_test.getAll();
    }

    @Test
    public void test_getAll_WithSyncCompleteCall_shouldReturnListFromDataProvider() throws Exception{
        SavingsAccount savingsAccount = new SavingsAccount();
        StubbedSavingsProvider dataProvider = new StubbedSavingsProvider(savingsAccount);
        FullfilledDataManager component_under_test = new FullfilledDataManager(dataProvider);

        component_under_test.sync();
        dataProvider.fireOnNextEvent();

        assertThat(component_under_test.getAll()).containsExactly(savingsAccount);
    }


    @Test
    public void test_add_shouldAddItemOnDataProvider() throws Exception{
        StubbedSavingsProvider dataProvider = new StubbedSavingsProvider();
        FullfilledDataManager component_under_test = new FullfilledDataManager(dataProvider);

        SavingsAccount savingsAccount = new SavingsAccount();
        component_under_test.add(savingsAccount, null);

        assertThat(dataProvider.accounts()).containsExactly(savingsAccount);
    }



    @Test(expected = IllegalStateException.class)
    public void test_initialState_get_shouldThrowIllegalArgumentException() throws Exception{
        FullfilledDataManager component_under_test = new FullfilledDataManager(new StubbedSavingsProvider());

        component_under_test.getAll();
    }

}
class FullfilledDataManager extends AbstractDataManager<SavingsAccount>{

    public FullfilledDataManager(DataProvider<SavingsAccount> dataProvider) {
        super(dataProvider);
    }
}
class StubbedSavingsProvider implements DataProvider<SavingsAccount>{

    private List<SavingsAccount> savings;
    private PublishSubject observable;
    StubbedSavingsProvider(SavingsAccount... savings){
        this.savings = new LinkedList<>(Arrays.asList(savings));
    }

    @Override
    public Observable<List<SavingsAccount>> get() {
        observable = PublishSubject.create();
        return this.observable;
    }

    public void fireOnNextEvent(){
        observable.onNext(this.savings);
        this.observable.onComplete();
    }

    @Override
    public Observable<String> add(SavingsAccount newItem) {
        this.savings.add(newItem);
        observable = PublishSubject.create();
        return this.observable;
    }

    public List<SavingsAccount> accounts(){
        return this.savings;
    }
}
