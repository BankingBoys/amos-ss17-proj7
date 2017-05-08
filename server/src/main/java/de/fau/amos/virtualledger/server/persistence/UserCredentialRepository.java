package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

/**
 * Created by Georg on 08.05.2017.
 */
@ApplicationScoped
public class UserCredentialRepository {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

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
