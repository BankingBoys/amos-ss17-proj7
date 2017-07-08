package de.fau.amos.virtualledger.android.views.contacts;

import java.util.Comparator;

import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 08.07.2017.
 */

public class ContactsComparator implements Comparator<Contact>{
    @Override
    public int compare(Contact c1, Contact c2) {
        return c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
    }
}
