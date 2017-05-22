package de.fau.amos.virtualledger.server.persistence;

import com.sun.istack.logging.Logger;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import de.fau.amos.virtualledger.server.model.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Georg on 22.05.2017.
 */

@ApplicationScoped
public class DeletedBankAccountsRepository {

    /**
     *
     */
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

    public void createDeletedBankAccount(DeletedBankAccount deletedBankAccount)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(deletedBankAccount);
                entityTransaction.commit();
            } catch(EntityExistsException entityExistsException) {
                logger().info("Entity already exists: "+ deletedBankAccount);
                entityTransaction.rollback();
                throw entityExistsException;
            } catch(IllegalArgumentException persistenceException) {
                logger().logException(persistenceException, Level.WARNING);
                entityTransaction.rollback();
                throw persistenceException;
            }
        }
        finally {
            entityManager.close();
        }
    }

    public void deleteDeletedBankAccountByEmailAndAccessId(final String email, final String accessId)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Query query = entityManager.createQuery("Select s FROM DeletedBankAccount s WHERE s.userEmail = :email AND s.bankAccessId = :accessId");
                query.setParameter("email", email);
                query.setParameter("accessId", accessId);
                List<Session> sessions = query.getResultList();

                for(int i = 0; i < sessions.size(); ++i)
                {
                    Session session = sessions.get(i);
                    entityManager.remove(session);
                }
                entityTransaction.commit();
            } catch(final Exception e) {
                logger().logException(e, Level.WARNING);
                entityTransaction.rollback();
                throw e;
            }
        }
        finally {
            entityManager.close();
        }
    }


    public List<DeletedBankAccount> getDeletedBankAccountIdsByEmailAndAccessId(final String email, final String accessId)
    {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager.createQuery("Select u FROM DeletedBankAccount u WHERE u.userEmail = :email AND u.bankAccessId = :accessId");
            query.setParameter("email", email);
            query.setParameter("accessId", accessId);
            final List resultList = query.getResultList();
            return resultList;
        } finally {
            entityManager.close();
        }
    }


    private Logger logger() {
        return Logger.getLogger(UserCredentialRepository.class);
    }
}
