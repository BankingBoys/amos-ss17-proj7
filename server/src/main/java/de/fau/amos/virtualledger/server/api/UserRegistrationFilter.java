package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.auth.UserAlreadyExistsException;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.model.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Filter that makes sure that the user is registered in the database, because registration is over 3rd party provider
 */
public class UserRegistrationFilter extends GenericFilterBean {

    private KeycloakUtilizer keycloakUtilizer;
    private AuthenticationController authenticationController;

    public UserRegistrationFilter(KeycloakUtilizer keycloakUtilizer, AuthenticationController authenticationController) {
        this.keycloakUtilizer = keycloakUtilizer;
        this.authenticationController = authenticationController;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String email = keycloakUtilizer.getEmail();
        String firstName = keycloakUtilizer.getFirstName();
        String lastName = keycloakUtilizer.getLastName();
        if (email == null || email.isEmpty()) {
            throw new ServletException("Username provided by authority must be specified");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new ServletException("First name provided by authority must be specified");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new ServletException("Family name provided by authority must be specified");
        }

        try {
            authenticationController.register(new User(email, firstName, lastName));
            logger.info("User " + email + " was registered!");
        } catch (UserAlreadyExistsException e) {
            logger.info("User " + email + " was already registered!");
        } catch (VirtualLedgerAuthenticationException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}
