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
     * @return
     */
    public String getEmail() {
        AccessToken token = null;
        try {
            token = getAccessToken();
        } catch (ServletException e) {
            return null;
        }
        return token.getEmail();
    }

    /**
     * extracts the first name from the keycloak context.
     * returns null if not successful.
     * @return
     */
    public String getFirstName() {
        AccessToken token = null;
        try {
            token = getAccessToken();
        } catch (ServletException e) {
            return null;
        }
        return token.getGivenName();
    }

    /**
     * extracts the last name from the keycloak context.
     * returns null if not successful.
     * @return
     */
    public String getLastName() {
        AccessToken token = null;
        try {
            token = getAccessToken();
        } catch (ServletException e) {
            return null;
        }
        return token.getFamilyName();
    }

    public String getTokenString() {
        //noinspection unchecked
        return ((KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getKeycloakSecurityContext().getTokenString();
    }

    private AccessToken getAccessToken() throws ServletException {
        final AccessToken token;
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            token = principal.getKeycloakSecurityContext().getToken();
        } catch (Exception ex) {
            throw new ServletException("Failure at getting data about the user by the identity token!");
        }
        return token;
    }
}
