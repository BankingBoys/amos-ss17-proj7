package de.fau.amos.virtualledger.server.auth;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

@Component
public class KeycloakUtilizer {

    /**
     * extracts the email adress from the keycloak context.
     * returns null if not successful.
     */
    public String getEmail() throws ServletException {
        return getAccessToken().getEmail();
    }

    /**
     * extracts the first name from the keycloak context.
     * returns null if not successful.
     */
    public String getFirstName() throws ServletException {
        return getAccessToken().getGivenName();
    }

    /**
     * extracts the last name from the keycloak context.
     * returns null if not successful.
     */
    public String getLastName() throws ServletException {
        return getAccessToken().getFamilyName();
    }

    private AccessToken getAccessToken() throws ServletException {
        return getKeycloakSecurityContext().getToken();
    }

    public String getTokenString() throws ServletException {
        return getKeycloakSecurityContext().getTokenString();
    }

    private KeycloakSecurityContext getKeycloakSecurityContext() throws ServletException {
        try {
            //noinspection unchecked
            return ((KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getKeycloakSecurityContext();
        } catch (final Exception e) {
            throw new ServletException("Failure at getting data about the user by the identity token!");
        }
    }
}
