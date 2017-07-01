package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.model.Contact;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsSupplier implements de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier<Contact>, Observer {
    @Inject
    ContactsDataManager contactsDataManager;

    private Set<DataListening> dataListenings = new HashSet<>();

    private List<Contact> allContacts = new ArrayList<>();

    public ContactsSupplier(Activity activity) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
    }

    @Override
    public List<Contact> getAll() {
        return new LinkedList<>(this.allContacts);
    }

    @Override
    public void onResume() {
        this.logger().log(Level.INFO, "Registering to contacts data manager");
        contactsDataManager.addObserver(this);
        this.logger().log(Level.INFO, "ContactsDataSyncStatus" + this.contactsDataManager.getSyncStatus());
        switch (contactsDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                contactsDataManager.sync();
                break;
            case SYNCED:
                onSavingsUpdated();
                break;
        }
    }

    private void onSavingsUpdated() {
        this.logger().info("Contacts loaded.");
        this.allContacts.clear();
        List<Contact> contacts= null;
        try {
            contacts = this.contactsDataManager.getContactsList();
        } catch (SyncFailedException e) {
            Log.e("", "Sync failed");
            return;
        }
        if(contacts != null){
            this.allContacts.addAll(contacts);
        }
        notifyObservers();
    }

    @Override
    public void addDataListeningObject(DataListening observer) {
        if (this.dataListenings.isEmpty()) {
            this.contactsDataManager.addObserver(this);
        }
        this.dataListenings.add(observer);
    }

    @Override
    public void deregister(DataListening observer) {
        this.dataListenings.remove(observer);
        if (this.dataListenings.isEmpty()) {
            this.contactsDataManager.deleteObserver(this);
        }
    }

    @Override
    public void onPause() {
        this.logger().log(Level.INFO, "De-Registering from contact data manager");
        this.contactsDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void notifyObservers() {
        this.logger().info("Notify "+this.dataListenings.size()+" Contacts-Listener with "+this.allContacts.size()+"contacts");
        for (DataListening dataListening : this.dataListenings) {
            dataListening.notifyDataChanged();
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }
}


