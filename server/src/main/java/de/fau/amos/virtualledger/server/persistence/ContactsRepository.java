package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.server.model.ContactsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class ContactsRepository {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PersistenceContext
    private EntityManager entityManager;


    public void createContact(final Contact contact, final String userEmail) {

        final int userId;
        try {
            final Query query = entityManager.createQuery("Select u.id FROM User u WHERE u.email = :email");
            query.setParameter("email", userEmail);
            final List resultList = query.getResultList();
            if (resultList.isEmpty()) {
                throw new IllegalArgumentException("User does not exist");
            }
            userId = (int) resultList.get(0);

            final ContactsEntity contactsEntity = new ContactsEntity();
            contactsEntity.setUserId(userId);
            contactsEntity.setEmail(contact.getEmail());
            contactsEntity.setFirstname(contact.getFirstName());
            contactsEntity.setLastname(contact.getLastName());

            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(contactsEntity);
                entityTransaction.commit();
            } catch (final EntityExistsException entityExistsException) {
                LOGGER.warn("Entity already exists: " + contactsEntity);
                entityTransaction.rollback();
                throw entityExistsException;
            }
        } catch (final Exception ex) {
            LOGGER.warn("", ex);
            throw ex;
        } finally {
            entityManager.close();
        }

    }

    public List<ContactsEntity> getContactsByEmail(final String email) {
        try {
            final Query query = entityManager
                    .createQuery("Select contacts FROM ContactsEntity contacts JOIN User user ON contacts.userId = user.id WHERE user.email = :email");
            query.setParameter("email", email);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
