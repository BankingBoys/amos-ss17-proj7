package de.fau.amos.virtualledger.server.auth;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUtilizer {

    /**
     * extracts the email adress from the keycloak context.
     * returns null if not successful.
     */
    public String getEmail() {
        return getAccessToken().getEmail();
    }

    /**
     * extracts the first name from the keycloak context.
     * returns null if not successful.
     */
    public String getFirstName() {
        return getAccessToken().getGivenName();
    }

    /**
     * extracts the last name from the keycloak context.
     * returns null if not successful.
     */
    public String getLastName() {
        return getAccessToken().getFamilyName();
    }

    private AccessToken getAccessToken() {
        return getKeycloakSecurityContext().getToken();
    }

    public String getAuthorizationHeader() {
        return "Bearer " + getTokenString();
    }

    public String getTokenString() {
        return getKeycloakSecurityContext().getTokenString();
    }

    private KeycloakSecurityContext getKeycloakSecurityContext() {
        try {
            //noinspection unchecked
            return ((KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getKeycloakSecurityContext();
        } catch (final Exception e) {
            throw new KeycloakException("Failure at getting data about the user by the identity token!");
        }
    }
}
