package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

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
}
