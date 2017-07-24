package de.fau.amos.virtualledger.android.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.api.shared.RetrofitMessagedException;
import de.fau.amos.virtualledger.android.api.sync.Toaster;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static de.fau.amos.virtualledger.android.data.SyncStatus.NOT_SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNC_IN_PROGRESS;

public class BankingDataManager extends Observable {
    private final static String TAG = BankingDataManager.class.getSimpleName();

    private Context context;
    private final BankingProvider bankingProvider;
    private final BankAccessCredentialDB bankAccessCredentialDB;
    private final AuthenticationProvider authenticationProvider;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    //Set if sync failed and thrown in getters
    private SyncFailedException syncFailedException = null;

    private SyncStatus syncStatus = NOT_SYNCED;
    private AtomicInteger syncsActive = new AtomicInteger(0);

    public BankingDataManager(final Context context, final BankingProvider bankingProvider, final BankAccessCredentialDB bankAccessCredentialDB, final AuthenticationProvider authenticationProvider) {
        this.context = context;
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
        syncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        syncsActive.addAndGet(1);
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
                syncFailedException = new SyncFailedException(throwable);
                onSyncComplete();
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
                final String pin = bankAccessCredentialDB.getPin(authenticationProvider.getUserId(), bankAccess.getId(), bankAccount.getBankid());
                if(pin != null) {
                    final BankAccountSync bankAccountSync = new BankAccountSync(bankAccess.getId(), bankAccount.getBankid(), pin);
                    bankAccountSyncList.add(bankAccountSync);
                }
            }
        }
        bankingProvider.getBankingTransactions(bankAccountSyncList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BankAccountSyncResult>() {
            @Override
            public void accept(@NonNull final BankAccountSyncResult bankAccountSyncResult) throws Exception {
                bankAccountBookings = bankAccountSyncResult.getBankaccountbookings();
                onSyncComplete();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                Log.e(TAG, "Failed getting bookings", throwable);
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

    /**
     * get all synced bankAccesses
     * @throws SyncFailedException if something has gone wrong during the syncing process
     * @throws SyncFailedException if getter is called while sync is in progress.
     */
    public List<BankAccess> getBankAccesses() throws SyncFailedException {
        if(syncFailedException != null) throw syncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return new LinkedList<>(bankAccesses);
    }

    /**
     * get all synced bankAccountBookings
     * @throws SyncFailedException if something has gone wrong during the syncing process
     * @throws SyncFailedException if getter is called while sync is in progress.
     */
    public List<BankAccountBookings> getBankAccountBookings() throws SyncFailedException {
        if(syncFailedException != null) throw syncFailedException;
        if(syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        return bankAccountBookings;
    }

    /**
     * Adds a BankAccess.
     * Therefore calls server API, stores on success in localStorage.
     * Afterwards syncs (-> Observers are notified after that)
     */
    public void addBankAccess(final BankAccessCredential bankAccessCredential) {
        bankingProvider.addBankAccess(bankAccessCredential)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BankAccess>() {
                    @Override
                    public void accept(@NonNull final BankAccess bankAccess) throws Exception {
                        for(BankAccount account: bankAccess.getBankaccounts()) {
                            bankAccessCredentialDB.persist(authenticationProvider.getUserId(), bankAccessCredential.getPin(), bankAccess.getId(), account.getBankid(), bankAccess.getName(), account.getName());
                        }
                        BankingDataManager.this.sync();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed adding a bank access", throwable);
                    }
                });
    }

    /**
     * Deletes a BankAccess.
     * Therefore calls server API, removes all associated accounts from localStorage on success.
     * Afterwards syncs (-> Observers are notified after that)
     */
    public void deleteBankAccess(final String accessId) {
        bankingProvider.deleteBankAccess(accessId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StringApiModel>() {
                    @Override
                    public void accept(@NonNull final StringApiModel stringApiModel) throws Exception {
                        Toaster toaster = new Toaster(context);
                        toaster.pushSuccessMessage("Bank Access was deleted.");
                        toaster.onOk();
                        for (final BankAccess bankAccess : bankAccesses) {
                            if (bankAccess.getId().equals(accessId)) {
                                for (final BankAccount bankAccount : bankAccess.getBankaccounts()) {
                                    bankAccessCredentialDB.delete(authenticationProvider.getUserId(), bankAccess.getId(), bankAccount.getBankid());
                                }
                            }
                        }
                        BankingDataManager.this.sync();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed deleting a bank access", throwable);

                        Toaster toaster = new Toaster(context);
                        if(throwable instanceof RetrofitMessagedException) {
                            RetrofitMessagedException messagedException = (RetrofitMessagedException) throwable;
                            toaster.pushSuccessMessage(messagedException.getMessage());
                        } else {
                            toaster.pushSuccessMessage("Deleting Bank Access failed.");
                        }
                        toaster.onTechnicalError();
                    }
                });
    }

    /**
     * Deletes a BankAccount.
     * Therefore calls server API, removes account from localStorage on success.
     * Afterwards syncs (-> Observers are notified after that)
     */
    public void deleteBankAccount(final String accessId, final String accountId) {
        bankingProvider.deleteBankAccount(accessId, accountId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StringApiModel>() {
                    @Override
                    public void accept(@NonNull final StringApiModel stringApiModel) throws Exception {
                        Toaster toaster = new Toaster(context);
                        toaster.pushSuccessMessage("Bank Account was deleted.");
                        toaster.onOk();
                        for (final BankAccess bankAccess : bankAccesses) {
                            for (final BankAccount bankAccount : bankAccess.getBankaccounts()) {
                                if(bankAccount.getBankid().equals(accountId)) {
                                    bankAccessCredentialDB.delete(authenticationProvider.getUserId(), bankAccess.getId(), bankAccount.getBankid());
                                }
                            }
                        }
                        BankingDataManager.this.sync();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed deleting a bank account", throwable);

                        Toaster toaster = new Toaster(context);
                        if(throwable instanceof RetrofitMessagedException) {
                            RetrofitMessagedException messagedException = (RetrofitMessagedException) throwable;
                            toaster.pushSuccessMessage(messagedException.getMessage());
                        } else {
                            toaster.pushSuccessMessage("Deleting Bank Account failed.");
                        }
                        toaster.onTechnicalError();
                    }
                });
    }
}
