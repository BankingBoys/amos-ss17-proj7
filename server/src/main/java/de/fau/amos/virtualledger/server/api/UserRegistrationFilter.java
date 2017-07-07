package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.UserAlreadyExistsException;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.model.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UserRegistrationFilter extends GenericFilterBean {

    @Autowired
    private AuthenticationController authenticationController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        IDToken token = null;
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            token = principal.getKeycloakSecurityContext().getToken();
        } catch (Exception ex) {
            throw new ServletException("Failure at getting data about the user by the identity token!");
        }
        String email = token.getEmail();
        String firstName = token.getGivenName();
        String lastName = token.getFamilyName();
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
        } catch (UserAlreadyExistsException e) {
            logger.info("User " + email + " already exists in database");
        } catch (VirtualLedgerAuthenticationException e) {
            throw new ServletException(e);
        }
    }
}
