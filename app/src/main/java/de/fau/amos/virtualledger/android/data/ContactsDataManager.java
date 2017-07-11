package de.fau.amos.virtualledger.android.data;

import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.api.sync.AbstractDataManager;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsDataManager extends AbstractDataManager<Contact> {
    public ContactsDataManager(final ContactsProvider contactsProvider) {
        super(contactsProvider);
    }
}
