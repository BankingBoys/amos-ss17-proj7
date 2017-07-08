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
        final Feature feature = new LoggingFeature(new JavaLoggerFacade(logger), Level.FINE, null, null);

        return ClientBuilder.newBuilder()
                .register(feature)
                .build();
    }

    private static class JavaLoggerFacade extends java.util.logging.Logger {
        private final Logger logger;

        JavaLoggerFacade(final Logger logger) {
            super("Jersey", null);
            this.logger = logger;
        }

        @Override
        public void fine(final String msg) {
            logger.debug(msg);
        }
    }
}
