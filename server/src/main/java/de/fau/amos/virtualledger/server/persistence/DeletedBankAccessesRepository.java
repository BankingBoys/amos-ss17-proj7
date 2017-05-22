package de.fau.amos.virtualledger.server.persistence;

import com.sun.istack.logging.Logger;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.Session;
import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Created by Georg on 22.05.2017.
 */
@ApplicationScoped
public class DeletedBankAccessesRepository {

    /**
     *
     */
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

    public void createDeletedBankAccess(DeletedBankAccess deletedBankAccess)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(deletedBankAccess);
                entityTransaction.commit();
            } catch(EntityExistsException entityExistsException) {
                logger().info("Entity already exists: "+ deletedBankAccess);
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

    public void deleteDeletedBankAccessByEmail(final String email)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Query query = entityManager.createQuery("Select s FROM DeletedBankAccess s WHERE s.userEmail = :email");
                query.setParameter("email", email);
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


    public List<DeletedBankAccess> getDeletedBankAccessIdsByEmail(final String email)
    {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Query query = entityManager.createQuery("Select u FROM DeletedBankAccess u WHERE u.userEmail = :email");
            query.setParameter("email", email);
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
