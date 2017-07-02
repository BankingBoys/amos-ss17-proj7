package de.fau.amos.virtualledger.server.jetty_test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

/**
 * Simple {@link ServletContextListener} to test gh-2058.
 */
@Component
public class ExampleServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("*** contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("*** contextDestroyed");
    }

}
