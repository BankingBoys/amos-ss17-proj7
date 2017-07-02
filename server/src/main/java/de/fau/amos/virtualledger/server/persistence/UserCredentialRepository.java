package de.fau.amos.virtualledger.server.persistence;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.model.Session;
import de.fau.amos.virtualledger.server.model.UserCredential;

/**
 * Repository class that allows CRUD operations on the databasse for
 * UserCredentials
 */
@Component
public class UserCredentialRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserCredentialRepository(EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }

    protected UserCredentialRepository() {
    };

    /**
     * looks up, if an user exists with a specific email address
     * 
     * @param email
     * @return
     */
    public boolean existsUserCredentialEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int countUserCredentials;
        try {
            Query query = entityManager.createQuery("Select u FROM UserCredential u WHERE u.email = :email");
            query.setParameter("email", email);
            countUserCredentials = query.getResultList().size();
        } catch (Exception ex) {
            LOGGER.warn("", ex);
            throw ex;
        } finally {
            entityManager.close();
        }
        return countUserCredentials != 0;
    }

    /**
     * creates a new UserCredential in the database
     * 
     * @param credential
     */
    public void createUserCredential(UserCredential credential) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(credential);
                entityTransaction.commit();
            } catch (EntityExistsException entityExistsException) {
                LOGGER.info("Entity already exists: " + credential);
                entityTransaction.rollback();
                throw entityExistsException;
            } catch (IllegalArgumentException persistenceException) {
                LOGGER.warn("", persistenceException);
                entityTransaction.rollback();
                throw persistenceException;
            }
        } finally {
            entityManager.close();
        }
    }

    public boolean checkLogin(final LoginData loginData) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager.createQuery("Select u FROM UserCredential u WHERE u.email = :email");
            query.setParameter("email", loginData.getEmail());
            final List resultList = query.getResultList();
            if (resultList.size() == 0) {
                return false;
            }
            final UserCredential userCredential = (UserCredential) resultList.get(0);
            return Objects.equals(userCredential.getPassword(), loginData.getPassword());
        } finally {
            entityManager.close();
        }
    }

    public void persistSessionId(final String email, final String sessionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Session session = new Session();
        session.setEmail(email);
        session.setSessionId(sessionId);

        try {
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(session);
                entityTransaction.commit();
            } catch (final Exception e) {
                LOGGER.warn("", e);
                entityTransaction.rollback();
                throw e;
            }
        } finally {
            entityManager.close();
        }
    }

    public void deleteSessionIdsByEmail(final String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Query query = entityManager.createQuery("Select s FROM Session s WHERE s.email = :email");
                query.setParameter("email", email);
                List<Session> sessions = query.getResultList();

                for (int i = 0; i < sessions.size(); ++i) {
                    Session session = sessions.get(i);
                    entityManager.remove(session);
                }
                entityTransaction.commit();
            } catch (final Exception e) {
                LOGGER.warn("", e);
                entityTransaction.rollback();
                throw e;
            }
        } finally {
            entityManager.close();
        }
    }

    public String getEmailForSessionId(final String sessionId) throws InvalidCredentialsException {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager.createQuery("Select s FROM Session s WHERE s.sessionId = :sessionId");
            query.setParameter("sessionId", sessionId);
            final List resultList = query.getResultList();
            if (resultList.size() == 0) {
                throw new InvalidCredentialsException();
            }
            final Session session = (Session) resultList.get(0);
            return session.getEmail();
        } finally {
            entityManager.close();
        }
    }
}
