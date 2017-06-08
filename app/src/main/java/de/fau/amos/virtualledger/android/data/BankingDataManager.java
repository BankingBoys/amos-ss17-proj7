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

public class BankingDataManager extends Observable {

    private final BankingProvider bankingProvider;
    private final BankAccessCredentialDB bankAccessCredentialDB;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    //Set if sync failed and thrwon in getters
    private BankingSyncFailedException bankingSyncFailedException = null;

    private boolean syncComplete = false;

    public BankingDataManager(final BankingProvider bankingProvider, final BankAccessCredentialDB bankAccessCredentialDB) {
        this.bankingProvider = bankingProvider;
        this.bankAccessCredentialDB = bankAccessCredentialDB;
    }

    public void sync(final String user) {
        bankingSyncFailedException = null;
        syncComplete = false;
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
                syncComplete = true;
                notifyObservers();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                bankingSyncFailedException = new BankingSyncFailedException(throwable);
            }
        });
    }

    public boolean isSyncComplete() {
        return syncComplete;
    }

    public List<BankAccess> getBankAccesses() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(!syncComplete) throw new IllegalStateException("Sync not completed");
        return bankAccesses;
    }

    public List<BankAccountBookings> getBankAccountBookings() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(!syncComplete) throw new IllegalStateException("Sync not completed");
        return bankAccountBookings;
    }
}
