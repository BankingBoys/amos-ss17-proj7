package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.dtos.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.invoke.MethodHandles;

@Component
public class ContactsRepository {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ContactsRepository(final EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }

    public void createContact(final Contact contact) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(contact);
                entityTransaction.commit();
            } catch (final EntityExistsException entityExistsException) {
                LOGGER.warn("Entity already exists: " + contact);
                entityTransaction.rollback();
                throw entityExistsException;
            }
        } finally {
            entityManager.close();
        }
    }
}
