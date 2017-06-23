package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.model.SavingsAccountToUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by Georg on 23.06.2017.
 */
@ApplicationScoped
public class SavingsAccountRepository {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    EntityManagerFactory entityManagerFactory;

    @Inject
    public SavingsAccountRepository(EntityManagerFactoryProvider entityManagerFactoryProvider) {
        this.entityManagerFactory = entityManagerFactoryProvider.getEntityManagerFactory();
    }
    protected SavingsAccountRepository() { };


}
