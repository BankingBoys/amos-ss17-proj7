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
    
}
