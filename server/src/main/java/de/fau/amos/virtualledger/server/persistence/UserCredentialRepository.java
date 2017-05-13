package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.server.model.Session;
import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Repository class that allows CRUD operations on the databasse for UserCredentials
 */
@ApplicationScoped
public class UserCredentialRepository {

    /**
     *
     */
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

    /**
     * looks up, if an user exists with a specific email address
     * @param email
     * @return
     */
    public boolean existsUserCredentialEmail(String email)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int countUserCredentials;
        try {
            Query query = entityManager.createQuery("Select u FROM UserCredential u WHERE u.email = :email");
            query.setParameter("email", email);
            countUserCredentials = query.getResultList().size();
        } catch (Exception ex)
        {
            throw ex;
        } finally
        {
            entityManager.close();
        }
        return countUserCredentials != 0;
    }

    /**
     * creates a new UserCredential in the database
     * @param credential
     */
    public void createUserCredential(UserCredential credential)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(credential);
                entityTransaction.commit();
            } catch(EntityExistsException entityExistsException) {
                entityTransaction.rollback();
                throw entityExistsException;
            } catch(IllegalArgumentException persistenceException) {
                entityTransaction.rollback();
                throw persistenceException;
            }
        }
        finally {
            entityManager.close();
        }
    }

    public boolean checkLogin(final LoginData loginData)
    {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager.createQuery("Select u FROM UserCredential u WHERE u.email = :email");
            query.setParameter("email", loginData.email);
            final List resultList = query.getResultList();
            if(resultList.size() == 0) {
                return false;
            }
            final UserCredential userCredential = (UserCredential) resultList.get(0);
            return Objects.equals(userCredential.getPassword(), loginData.password);
        } finally {
            entityManager.close();
        }
    }

    public void persistSessionId(final String email, final String sessionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Session session = new Session();
        session.email = email;
        session.sessionId = sessionId;

        try{
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(session);
                entityTransaction.commit();
            } catch(final Exception e) {
                entityTransaction.rollback();
                throw e;
            }
        }
        finally {
            entityManager.close();
        }
    }
}
