package de.fau.amos.virtualledger.android.data;

import java.util.List;
import java.util.Observable;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static de.fau.amos.virtualledger.android.data.BankingDataManager.SYNC_STATUS.*;

public class BankingDataManager extends Observable {

    public enum SYNC_STATUS {
        NOT_SYNCED, SYNC_IN_PROGRESS, SYNCED
    }

    private final BankingProvider bankingProvider;
    private final BankAccessCredentialDB bankAccessCredentialDB;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    //Set if sync failed and thrwon in getters
    private BankingSyncFailedException bankingSyncFailedException = null;

    private SYNC_STATUS syncStatus = NOT_SYNCED;

    public BankingDataManager(final BankingProvider bankingProvider, final BankAccessCredentialDB bankAccessCredentialDB) {
        this.bankingProvider = bankingProvider;
        this.bankAccessCredentialDB = bankAccessCredentialDB;
    }

    public void sync(final String user) {
        bankingSyncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        bankingProvider.getBankingOverview()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BankAccess>>() {
            @Override
            public void accept(@NonNull final List<BankAccess> bankAccesses) throws Exception {
                BankingDataManager.this.bankAccesses = bankAccesses;
                syncBookings(user);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                bankingSyncFailedException = new BankingSyncFailedException(throwable);
            }
        });
    }

    private void syncBookings(final String user) {
        final List<BankAccountSync> bankAccountSyncList = bankAccessCredentialDB.getBankAccountSyncList(user);
        bankingProvider.getBankingTransactions(bankAccountSyncList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BankAccountSyncResult>() {
            @Override
            public void accept(@NonNull final BankAccountSyncResult bankAccountSyncResult) throws Exception {
                bankAccountBookings = bankAccountSyncResult.getBankaccountbookings();
                syncStatus = SYNCED;
                setChanged();
                notifyObservers();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                bankingSyncFailedException = new BankingSyncFailedException(throwable);
            }
        });
    }

    public SYNC_STATUS getSyncStatus() {
        return syncStatus;
    }

    public List<BankAccess> getBankAccesses() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return bankAccesses;
    }

    public List<BankAccountBookings> getBankAccountBookings() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return bankAccountBookings;
    }
}
