package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.BankAccess;
import de.fau.amos.virtualledger.server.model.BankAccount;

import javax.persistence.*;

/**
 * Created by ramimahfoud on 21/05/2017.
 */
public class BankAccountRepository {

    /**
     * creates a new UserCredential in the database
     * @param credential
     */

    /**
     *
     */
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

    public void createAccount(BankAccount account)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(account);
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
