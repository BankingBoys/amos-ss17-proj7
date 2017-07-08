package de.fau.amos.virtualledger.android.api.contacts;

import java.util.List;

import de.fau.amos.virtualledger.dtos.Contact;
import io.reactivex.Observable;

public interface ContactsProvider {

    Observable<List<Contact>> getContacts();

    Observable<Void> addContact(String email);

}
