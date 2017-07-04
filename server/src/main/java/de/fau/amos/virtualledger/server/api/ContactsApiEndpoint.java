package de.fau.amos.virtualledger.server.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

/**
 * Endpoints for contacts
 */
@RestController
public class ContactsApiEndpoint {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    public ContactsApiEndpoint() {

    }

}
