package de.fau.amos.virtualledger.android.views.contacts.delete;

import android.app.Activity;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.sync.Toaster;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 18.07.2017.
 */

public class ContactsDeleteAction {

    @Inject
    ContactsDataManager contactsDataManager;
    private Contact contact;
    Toaster toaster;

    public ContactsDeleteAction(Activity activity, Contact contact) {
        ((App) activity.getApplication()).getNetComponent().inject(this);
        this.contact = contact;
        toaster = new Toaster(activity)
                .pushConceptualErrorMessage("User not found");
    }

    public void delete() {
        contactsDataManager.delete(contact, toaster);
    }
}
