package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.contacts.ContactsController;
import de.fau.amos.virtualledger.server.contacts.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.lang.invoke.MethodHandles;

/**
 * Endpoints for contacts
 */
@RestController
public class ContactsApiEndpoint {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ContactsController contactsController;
    private KeycloakUtilizer keycloakUtilizer;

    @Autowired
    public ContactsApiEndpoint(KeycloakUtilizer keycloakUtilizer, final ContactsController contactsController) {
        this.keycloakUtilizer = keycloakUtilizer;
        this.contactsController = contactsController;
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/contacts", produces = "application/json")
    public ResponseEntity<?> getContactsEndpoint() throws ServletException {
        final String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("getContactsEndpoint of " + username + " was requested");

        ResponseEntity<?> entity;
        try {
            entity = this.getContacts(username);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return entity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/contacts", produces = "application/json")
    public ResponseEntity<?> addContactEndpoint(@RequestBody final Contact contact) throws ServletException {
        final String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("addContactEndpoint of " + username + " was requested");

        return this.addContact(contact, username);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "api/contacts", produces = "application/json")
    public ResponseEntity<?> deleteContactEndpoint(@RequestBody final Contact contact) throws ServletException {
        final String username = keycloakUtilizer.getEmail();
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("deleteContactEndpoint of " + username + " was requested");
        return this.deleteContact(contact, username);
    }

    private ResponseEntity<?> getContacts(final String username) throws UserNotFoundException {
        return new ResponseEntity<>(contactsController.getContactsByEmail(username), HttpStatus.OK);
    }

    private ResponseEntity<?> addContact(final Contact contact, final String username) {
        try {
            contactsController.addContact(contact, username);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("Adding of Contacts Successful", HttpStatus.OK);
    }

    private ResponseEntity<?> deleteContact(final Contact contact, final String username) {
        try {
            contactsController.deleteContact(contact, username);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Deleting of Contact successful", HttpStatus.OK);
    }
}
