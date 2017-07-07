package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

/**
 * Repository class that allows CRUD operations on the databasse for
 * Users
 */
@Component
public class UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepository() {
    }

    /**
     * Constructor for testing -> inject EntityManager
     * @param entityManager
     */
    protected UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * looks up, if an user exists with a specific email address
     *
     * @param email
     * @return
     */
    public boolean existsUserWithEmail(String email) {
        int countUsers;
        try {
            Query query = entityManager.createQuery("Select u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            countUsers = query.getResultList().size();
        } catch (Exception ex) {
            LOGGER.warn("", ex);
            throw ex;
        } finally {
            entityManager.close();
        }
        return countUsers != 0;
    }

    /**
     * creates a new UserCredential in the database
     * 
     * @param user
     */
    @Transactional
    public void createUser(User user) {
        try {
            entityManager.persist(user);
        } catch (EntityExistsException entityExistsException) {
            LOGGER.info("Entity already exists: " + user);
            throw entityExistsException;
        } catch (IllegalArgumentException persistenceException) {
            LOGGER.warn("", persistenceException);
            throw persistenceException;
        } finally {
            entityManager.close();
        }
    }
}
