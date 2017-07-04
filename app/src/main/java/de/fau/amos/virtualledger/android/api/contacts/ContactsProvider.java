package de.fau.amos.virtualledger.android.api.contacts;

import java.util.List;

import de.fau.amos.virtualledger.dtos.Contact;
import io.reactivex.Observable;

/**
 * Created by Simon on 01.07.2017.
 */

public interface ContactsProvider {

    Observable<List<Contact>> getContacts();

    Observable<String> addContacts(Contact savingsAccount);

}
