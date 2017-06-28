package de.fau.amos.virtualledger.server.persistence;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("request")
public class EntityManagerFactoryProvider {

    EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryProvider()
    {
        if(entityManagerFactory == null) {
            initEntityManagerFactory();
        }
    }

    /**
     * Provides a configured EntityManagerFactory that requires a environment variable VIRTUAL_LEDGER_DB_PASSWORD with the password of the database "auth-db"
     * @return
     */
    public EntityManagerFactory getEntityManagerFactory() {

        if(entityManagerFactory == null) {
            initEntityManagerFactory();
        }
        return entityManagerFactory;
    }

    private void initEntityManagerFactory() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        for (String envName : env.keySet()) {
            if (envName.equals("VIRTUAL_LEDGER_DB_PASSWORD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        }

        entityManagerFactory = Persistence.createEntityManagerFactory("auth-db", configOverrides);
    }
}
