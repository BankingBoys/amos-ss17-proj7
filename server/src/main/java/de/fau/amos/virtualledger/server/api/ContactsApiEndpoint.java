package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.contacts.ContactsController;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

/**
 * Endpoints for contacts
 */
@RestController
public class ContactsApiEndpoint {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ContactsController contactsController;

    @Autowired
    public ContactsApiEndpoint(final ContactsController contactsController) {
        this.contactsController = contactsController;
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/contacts", produces = "application/json")
    public ResponseEntity<?> getContactsEndpoint() {
        final KeycloakPrincipal principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = principal.getKeycloakSecurityContext().getToken().getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("getContactsEndpoint of " + username + " was requested");

        return this.getContacts(username);
    }

    private ResponseEntity<?> getContacts(final String username) {
        return new ResponseEntity<>(contactsController.getContactsByEmail(username), HttpStatus.OK);
    }

}
