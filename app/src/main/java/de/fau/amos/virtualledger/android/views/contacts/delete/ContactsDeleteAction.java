package de.fau.amos.virtualledger.android.views.contacts.delete;

import android.app.Activity;
import android.content.Context;

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

    public ContactsDeleteAction(Context context, Contact contact) {
        ((App) context.getApplicationContext()).getNetComponent().inject(this);
        this.contact = contact;
        toaster = new Toaster(context.getApplicationContext())
                .pushConceptualErrorMessage("User not found").pushSuccessMessage("User " + contact.getEmail() + " was successfully deleted");
    }

    public void delete() {
        contactsDataManager.delete(contact, toaster);
    }
}
