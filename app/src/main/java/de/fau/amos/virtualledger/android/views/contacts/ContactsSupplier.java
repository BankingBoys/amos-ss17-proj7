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
    }

    @Override
    public void addDataListeningObject(DataListening observer) {

    }

    @Override
    public void deregister(DataListening observer) {

    }

    @Override
    public void onPause() {

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


