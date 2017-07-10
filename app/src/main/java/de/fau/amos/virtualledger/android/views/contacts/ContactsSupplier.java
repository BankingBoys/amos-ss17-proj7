package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Activity;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.sync.ConcreteSupplier;
import de.fau.amos.virtualledger.android.api.sync.DataManager;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsSupplier extends ConcreteSupplier<Contact> {

    @Inject
    ContactsDataManager contactsDataManager;


    public ContactsSupplier(Activity activity) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
    }

    @Override
    protected DataManager<Contact> dataManager() {
        return this.contactsDataManager;
    }
}