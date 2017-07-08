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

    public String getTokenString() {
        //noinspection unchecked
        return ((KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getKeycloakSecurityContext().getTokenString();
    }

    private AccessToken getAccessToken() throws ServletException {
        try {
            @SuppressWarnings("unchecked") final KeycloakPrincipal principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal.getKeycloakSecurityContext().getToken();
        } catch (final Exception ex) {
            throw new ServletException("Failure at getting data about the user by the identity token!");
        }
    }
}
