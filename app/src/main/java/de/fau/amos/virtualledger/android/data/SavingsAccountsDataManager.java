package de.fau.amos.virtualledger.android.data;

import android.util.Log;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.dtos.AddSavingsAccountData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static de.fau.amos.virtualledger.android.data.SyncStatus.NOT_SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNC_IN_PROGRESS;

public class SavingsAccountsDataManager extends Observable {
    private final static String TAG = SavingsAccountsDataManager.class.getSimpleName();

    private final SavingsProvider savingsProvider;

    private List<SavingsAccount> savingsAccounts;

    //Set if sync failed and thrown in getters
    private SyncFailedException syncFailedException = null;

    private SyncStatus syncStatus = NOT_SYNCED;
    private AtomicInteger syncsActive = new AtomicInteger(0);

    public SavingsAccountsDataManager(final SavingsProvider savingsProvider) {
        this.savingsProvider = savingsProvider;
    }

    /**
     * Syncs all bankAccess and booking data with server.
     * Therefore the syncStatus goes firs into SYNC_IN_PROGRESS and back into SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    public void sync() {
        syncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        syncsActive.addAndGet(1);

        savingsProvider.getSavingAccounts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SavingsAccount>>() {
                    @Override
                    public void accept(@NonNull final List<SavingsAccount> savingsAccounts) throws Exception {
                        SavingsAccountsDataManager.this.savingsAccounts = savingsAccounts;
                        onSyncComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed getting savings accounts", throwable);
                        syncFailedException = new SyncFailedException(throwable);
                        onSyncComplete();
                    }
                });
    }

    private void onSyncComplete() {
        final int syncsLeft = syncsActive.decrementAndGet();
        if(syncsLeft == 0) {
            syncStatus = SYNCED;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * gets the status of the BankingDataManager.
     * NOT_SYNCED if no sync was done yet.
     * SYNC_IN_PROGRESS if the sync is in progress yet.
     * SYNCED if a sync was done.
     */
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public List<SavingsAccount> getSavingsAccounts() throws SyncFailedException {
        if(syncFailedException != null) throw syncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return savingsAccounts;
    }

    public void addSavingsAccount(final AddSavingsAccountData addSavingsAccountData) {
        //TODO use dtos from library for everything
        final SavingsAccount savingsAccount = new SavingsAccount("", addSavingsAccountData.name, addSavingsAccountData.goalBalance, 0, addSavingsAccountData.finalDate);
        savingsProvider.addSavingAccount(savingsAccount)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String result) throws Exception {
                        SavingsAccountsDataManager.this.sync();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed adding savings account", throwable);
                    }
                });
    }


}
