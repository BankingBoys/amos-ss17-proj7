package de.fau.amos.virtualledger.android.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static de.fau.amos.virtualledger.android.data.BankingDataManager.SYNC_STATUS.NOT_SYNCED;
import static de.fau.amos.virtualledger.android.data.BankingDataManager.SYNC_STATUS.SYNCED;
import static de.fau.amos.virtualledger.android.data.BankingDataManager.SYNC_STATUS.SYNC_IN_PROGRESS;

public class BankingDataManager extends Observable {
    private final static String TAG = BankingDataManager.class.getSimpleName();


    public enum SYNC_STATUS {
        NOT_SYNCED, SYNC_IN_PROGRESS, SYNCED
    }
    private final BankingProvider bankingProvider;
    private final BankAccessCredentialDB bankAccessCredentialDB;
    private final AuthenticationProvider authenticationProvider;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    //Set if sync failed and thrwon in getters
    private BankingSyncFailedException bankingSyncFailedException = null;

    private SYNC_STATUS syncStatus = NOT_SYNCED;

    public BankingDataManager(final BankingProvider bankingProvider, final BankAccessCredentialDB bankAccessCredentialDB, final AuthenticationProvider authenticationProvider) {
        this.bankingProvider = bankingProvider;
        this.bankAccessCredentialDB = bankAccessCredentialDB;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Syncs all bankAccess and booking data with server.
     * Therefore the syncStatus goes firs into SYNC_IN_PROGRESS and back into SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    public void sync() {
        bankingSyncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        bankingProvider.getBankingOverview()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BankAccess>>() {
            @Override
            public void accept(@NonNull final List<BankAccess> bankAccesses) throws Exception {
                BankingDataManager.this.bankAccesses = bankAccesses;
                syncBookings();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                Log.e(TAG, "Failed getting bankOverview", throwable);
                bankingSyncFailedException = new BankingSyncFailedException(throwable);
            }
        });
    }

    /**
     * Method that handles sync'ing the booking data with the server.
     * Therefore the syncStatus set to SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    private void syncBookings() {
        final List<BankAccountSync> bankAccountSyncList = new ArrayList<>();
        for (BankAccess bankAccess : bankAccesses) {
            for (BankAccount bankAccount: bankAccess.getBankaccounts()) {
                final String pin = bankAccessCredentialDB.getPin(authenticationProvider.getEmail(), bankAccess.getBankcode(), bankAccess.getBanklogin(), bankAccess.getId(), bankAccount.getBankid());
                final BankAccountSync bankAccountSync = new BankAccountSync(bankAccess.getId(), bankAccount.getBankid(), pin);
                bankAccountSyncList.add(bankAccountSync);
            }
        }
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
                Log.e(TAG, "Failed getting bookings", throwable);
                bankingSyncFailedException = new BankingSyncFailedException(throwable);
            }
        });
    }

    /**
     * gets the status of the BankingDataManager.
     * NOT_SYNCED if no sync was done yet.
     * SYNC_IN_PROGRESS if the sync is in progress yet.
     * SYNCED if a sync was done.
     * @return
     */
    public SYNC_STATUS getSyncStatus() {
        return syncStatus;
    }

    /**
     * get all synced bankAccesses
     * @return
     * @throws BankingSyncFailedException if something has gone wrong during the syncing process
     * @throws BankingSyncFailedException if getter is called while sync is in progress.
     */
    public List<BankAccess> getBankAccesses() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return bankAccesses;
    }

    /**
     * get all synced bankAccountBookings
     * @return
     * @throws BankingSyncFailedException if something has gone wrong during the syncing process
     * @throws BankingSyncFailedException if getter is called while sync is in progress.
     */
    public List<BankAccountBookings> getBankAccountBookings() throws BankingSyncFailedException {
        if(bankingSyncFailedException != null) throw bankingSyncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return bankAccountBookings;
    }

    /**
     * Adds a BankAccess.
     * Therefore calls server API, stores on success in localStorage.
     * Afterwars syncs (-> Observers are notified after that)
     * @param bankAccessCredential
     * @throws BankingAddFailedException
     */
    public void addBankAccess(final BankAccessCredential bankAccessCredential) throws BankingAddFailedException {
        bankingProvider.addBankAccess(bankAccessCredential)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BankAccess>() {
                    @Override
                    public void accept(@NonNull final BankAccess bankAccess) throws Exception {
                        BankingDataManager.this.sync();
                        for(BankAccount account: bankAccess.getBankaccounts()) {
                            bankAccessCredentialDB.persist(authenticationProvider.getEmail(), bankAccess.getBankcode(), bankAccess.getBanklogin(), bankAccessCredential.getPin(), bankAccess.getId(), account.getBankid(), bankAccess.getName(), account.getName());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed adding a bank access", throwable);
                        throw new BankingAddFailedException(throwable);
                    }
                });
    }
}
