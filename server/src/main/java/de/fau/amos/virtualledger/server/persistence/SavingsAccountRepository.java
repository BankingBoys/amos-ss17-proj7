package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.model.SavingsAccountToUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Component

public class SavingsAccountRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PersistenceContext
    private EntityManager entityManager;

    public void createSavingsAccount(String email, SavingsAccount savingsAccount) {
        try {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(savingsAccount);
                entityManager.flush();
                entityManager.persist(new SavingsAccountToUser(email, savingsAccount.getId()));
                entityTransaction.commit();
            } catch (EntityExistsException entityExistsException) {
                LOGGER.info("Entity already exists: " + savingsAccount);
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

    public List<SavingsAccount> getSavingsAccountsByUserEmail(final String email) {
        try {
            final Query query = entityManager.createQuery(
                    "Select u FROM SavingsAccount u JOIN SavingsAccountToUser s WHERE u.id = s.idSavingsaccount AND s.email = :email");
            query.setParameter("email", email);
            final List resultList = query.getResultList();
            return resultList;
        } finally {
            entityManager.close();
        }
    }

    public void deleteSavingsAccountById(final int id) {

        try {
            final EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Query query = entityManager.createQuery("Select s FROM SavingsAccount s WHERE s.id = :id");
                query.setParameter("id", id);
                List<SavingsAccount> savingsAccountList = query.getResultList();
                for (int i = 0; i < savingsAccountList.size(); ++i) {
                    SavingsAccount savingsAccount = savingsAccountList.get(i);
                    entityManager.remove(savingsAccount);
                }

                query = entityManager.createQuery(
                        "Select s FROM SavingsAccountToUser s WHERE s.idSavingsaccount = :idSavingsaccount");
                query.setParameter("idSavingsaccount", id);
                List<SavingsAccountToUser> savingsAccountToUserList = query.getResultList();
                for (int i = 0; i < savingsAccountToUserList.size(); ++i) {
                    SavingsAccountToUser savingsAccountToUser = savingsAccountToUserList.get(i);
                    entityManager.remove(savingsAccountToUser);
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
}
