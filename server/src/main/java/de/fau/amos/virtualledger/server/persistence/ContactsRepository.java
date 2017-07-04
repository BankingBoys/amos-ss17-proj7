package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.ContactsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class ContactsRepository {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ContactsRepository(final EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }

    public void createContact(final ContactsEntity contactsEntity) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
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
        } finally {
            entityManager.close();
        }
    }

    public List<ContactsEntity> getContactsByEmail(final String email) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager
                    .createQuery("Select contacts FROM ContactsEntity contacts JOIN UserCredential user ON contacts.userId = user.id WHERE user.email = :email");
            query.setParameter("email", email);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
