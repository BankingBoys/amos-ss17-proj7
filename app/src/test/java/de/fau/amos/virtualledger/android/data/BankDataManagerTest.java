package de.fau.amos.virtualledger.android.data;

import android.app.Application;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccess;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.mock;


public class BankDataManagerTest {
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
    public void teste_synchonisationStart_syncStatusShouldChangeToSync() {
        // SETUP
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider();
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        Assertions.assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.NOT_SYNCED);
        component_under_test.sync();

        // ASSERT
        Assertions.assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.SYNC_IN_PROGRESS);
    }

    @Test(expected = IllegalStateException.class)
    public void teste_synchonisationStart_shouldThrowExceptionOnGetBankAccesses() throws Exception{
        // SETUP
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider();
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        component_under_test.getBankAccesses();
    }

    @Test(expected = IllegalStateException.class)
    public void teste_synchonisationStart_shouldThrowExceptionOnBankAccountBookings() throws Exception{
        // SETUP
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider();
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        component_under_test.getBankAccountBookings();
    }


    @Test
    public void teste_synchonisation_withSyncFinishedOnManager_shouldReturnSyncStatusSynced() {
        // SETUP
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider();
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        component_under_test.sync();
        bankingProvider.notifyNext();

        // ASSERT
        Assertions.assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.SYNCED);
    }


    @Test
    public void teste_synchonisation_withSyncFinishedOnManager_shouldNotifyObserversWhenFinished() {
        // SETUP
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider();
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        component_under_test.sync();
        bankingProvider.notifyNext();
        bankingProvider.notifyOnComplete();

        // ASSERT
        Assertions.assertThat(o.isNotified()).isTrue();
    }


    @Test
    public void teste_getBankAccesses_shouldReturnItems_afterSyncComplete() throws Exception {
        // SETUP
        BankAccess access = new BankAccess();
        StubbedBankingProvider bankingProvider = new StubbedBankingProvider(access);
        Application context = mock(Application.class);
        BankingDataManager component_under_test = new BankingDataManager(
                context,
                bankingProvider, //
                mock(BankAccessCredentialDB.class),//
                mock(AuthenticationProvider.class));
        TestObserver o = new TestObserver();
        component_under_test.addObserver(o);

        // ACT
        component_under_test.sync();
        bankingProvider.notifyNext();
        bankingProvider.notifyOnComplete();

        // ASSERT
        Assertions.assertThat(component_under_test.getBankAccesses()).containsOnly(access);
    }
}

class TestObserver implements Observer {
    private boolean notified = false;

    @Override
    public void update(Observable observable, Object o) {
        notified = true;
    }

    boolean isNotified() {
        return notified;
    }
}
