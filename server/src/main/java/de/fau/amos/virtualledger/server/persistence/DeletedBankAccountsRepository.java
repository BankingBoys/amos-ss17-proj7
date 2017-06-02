package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
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
public class DeletedBankAccountsRepository {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    EntityManagerFactory entityManagerFactory;

    @Inject
    public DeletedBankAccountsRepository(EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }
    protected DeletedBankAccountsRepository() { };

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
                logger.info("Entity already exists: "+ deletedBankAccount);
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
                List<DeletedBankAccount> deletedBankAccountList = query.getResultList();

                for(int i = 0; i < deletedBankAccountList.size(); ++i)
                {
                    DeletedBankAccount deletedBankAccount = deletedBankAccountList.get(i);
                    entityManager.remove(deletedBankAccount);
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

}
