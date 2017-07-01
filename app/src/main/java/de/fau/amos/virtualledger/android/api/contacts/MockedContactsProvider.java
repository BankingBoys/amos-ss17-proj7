package de.fau.amos.virtualledger.android.api.contacts;

import java.util.List;

import de.fau.amos.virtualledger.android.model.Contact;
import io.reactivex.Observable;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedContactsProvider implements ContactsProvider {

    @Override
    public Observable<List<Contact>> getContacts() {
        return null;
    }

    @Override
    public Observable<String> addContacts(Contact savingsAccount) {
        return null;
    }
}
