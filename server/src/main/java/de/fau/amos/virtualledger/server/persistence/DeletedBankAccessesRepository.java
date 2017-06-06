package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by Georg on 22.05.2017.
 */
@ApplicationScoped
public class DeletedBankAccessesRepository {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    EntityManagerFactory entityManagerFactory;

    @Inject
    public DeletedBankAccessesRepository(EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }
    protected DeletedBankAccessesRepository() { }

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
                logger.info("Entity already exists: "+ deletedBankAccess);
                entityTransaction.rollback();
                throw entityExistsException;
            } catch(IllegalArgumentException persistenceException) {
                logger.warn("", persistenceException);
                entityTransaction.rollback();
                throw persistenceException;
            }
        }
        finally {
            entityManager.close();
        }
    }

    public void deleteDeletedBankAccessByEmailAndId(final String email, final String accessId)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Query query = entityManager.createQuery("Select s FROM DeletedBankAccess s WHERE s.userEmail = :email AND s.bankAccessId = :accessId");
                query.setParameter("email", email);
                query.setParameter("accessId", accessId);
                List<DeletedBankAccess> deletedBankAccessList = query.getResultList();

                for(int i = 0; i < deletedBankAccessList.size(); ++i)
                {
                    DeletedBankAccess deletedBankAccess = deletedBankAccessList.get(i);
                    entityManager.remove(deletedBankAccess);
                }
                entityTransaction.commit();
            } catch(final Exception e) {
                logger.warn("", e);
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

}
