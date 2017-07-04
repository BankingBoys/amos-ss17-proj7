package de.fau.amos.virtualledger.server.contacts;

import de.fau.amos.virtualledger.server.persistence.ContactsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ContactsController {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ContactsRepository contactsRepository;

    @Autowired
    public ContactsController(final ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }
}
