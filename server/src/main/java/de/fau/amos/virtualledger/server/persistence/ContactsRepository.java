package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.server.model.ContactsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactsRepository {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PersistenceContext
    private EntityManager entityManager;


    public void createContact(final Contact contact, final String userEmail) {

    }

    public List<ContactsEntity> getContactsByEmail(final String email) {
        return new ArrayList<ContactsEntity>();
    }
}
