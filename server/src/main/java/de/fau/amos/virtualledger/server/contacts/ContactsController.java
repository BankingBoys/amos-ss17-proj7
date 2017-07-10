package de.fau.amos.virtualledger.server.contacts;

import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.server.persistence.ContactsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactsController {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ContactsRepository contactsRepository;

    @Autowired
    public ContactsController(final ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    public List<Contact> getContactsByEmail(final String email) {
        return new ArrayList<Contact>();
    }

    public void addContact(final Contact contact, final String userEmail) {
        contactsRepository.createContact(contact, userEmail);
    }
}
