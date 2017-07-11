package de.fau.amos.virtualledger.server.banking.adorsys.api;

import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;
import java.util.logging.Level;

public class JerseyClientUtility {

    private JerseyClientUtility() {
        //nothing to do, because utility class
    }

    public static Client getLoggingClient(final Logger logger) {
        final Feature feature = new LoggingFeature(java.util.logging.Logger.getLogger(logger.getName()), Level.INFO, null, null);

        return ClientBuilder.newBuilder()
                .register(feature)
                .build();
    }

}
