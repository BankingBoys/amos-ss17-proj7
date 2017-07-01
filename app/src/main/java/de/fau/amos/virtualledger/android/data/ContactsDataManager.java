package de.fau.amos.virtualledger.android.data;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.model.Contact;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Observable;

import static de.fau.amos.virtualledger.android.data.SyncStatus.NOT_SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNCED;
import static de.fau.amos.virtualledger.android.data.SyncStatus.SYNC_IN_PROGRESS;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsDataManager extends Observable {
    private final static String TAG = SavingsAccountsDataManager.class.getSimpleName();

    private final ContactsProvider contactsProvider;

    private List<Contact> contactsList = new LinkedList<>();

    //Set if sync failed and thrown in getters
    private SyncFailedException syncFailedException = null;

    private SyncStatus syncStatus = NOT_SYNCED;
    private AtomicInteger syncsActive = new AtomicInteger(0);

    public ContactsDataManager(final ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
    }

    /**
     * Syncs all contacts with server.
     * Therefore the syncStatus goes firs into SYNC_IN_PROGRESS and back into SYNCED when finished.
     * Also notifies all Observers that changes were made.
     */
    public void sync() {
        this.contactsList = new LinkedList<>();
        syncFailedException = null;
        syncStatus = SYNC_IN_PROGRESS;
        syncsActive.addAndGet(1);
        contactsProvider.getContacts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(@NonNull final List<Contact> contacts) throws Exception {
                        ContactsDataManager.this.contactsList.addAll(contacts);
                        onSyncComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        Log.e(TAG, "Failed getting contacts", throwable);
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

    /**
     * gets the status of the ContactsDataManager
     * NOT_SYNCED if no sync was done yet.
     * SYNC_IN_PROGRESS if the sync is in progress yet.
     * SYNCED if a sync was done.
     */
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public List<Contact> getContactsList() throws SyncFailedException {
        if (syncFailedException != null) throw syncFailedException;
        if (syncStatus != SYNCED) throw new IllegalStateException("Sync not completed");
        logger().info("Number of Contacts synct: " + this.contactsList.size());
        return new LinkedList<>(contactsList);
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.hashCode() + "}");
    }


}
