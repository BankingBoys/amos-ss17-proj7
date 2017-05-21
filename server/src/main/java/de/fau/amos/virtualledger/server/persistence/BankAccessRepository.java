package de.fau.amos.virtualledger.server.persistence;
 import de.fau.amos.virtualledger.server.model.BankAccess;
 import de.fau.amos.virtualledger.server.model.BankAccount;

 import javax.persistence.*;
import java.util.List;
import java.util.Objects;
/**
 * Created by ramimahfoud on 21/05/2017.
 */
public class BankAccessRepository {



    /**
     *
     */
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auth-db");

    public void createAccess(BankAccess access)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.persist(access);
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

    public void deleteAccess(BankAccess access){


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                BankAccess deleteAccount=entityManager.find(BankAccess.class,access.getId());

                entityTransaction.begin();
                entityManager.remove(deleteAccount);
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

    public void deleteAccount(BankAccount account){


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {

                BankAccount deleteAccount=entityManager.find(BankAccount.class,account.getId());
                entityTransaction.begin();
                entityManager.remove(deleteAccount);
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
