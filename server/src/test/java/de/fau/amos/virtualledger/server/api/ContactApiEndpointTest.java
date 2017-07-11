package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.contacts.ContactsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Simon on 11.07.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ContactApiEndpointTest {

    @Mock
    private ContactsController contactsController;

    @Test
    public void getContactsEndpointUserNameNull() throws ServletException {
        ContactsApiEndpoint contactsApiEndpoint = new ContactsApiEndpoint(setupKeycloakUtilizer(null), contactsController);


        ResponseEntity<?> responseEntity = contactsApiEndpoint.getContactsEndpoint();


        HttpStatus expected = HttpStatus.FORBIDDEN;
        assertThat(responseEntity.getStatusCode()).isEqualTo(expected);
        try {
            verify(contactsController, times(0)).getContactsByEmail(any(String.class));
        } catch (Exception e) {
            //Do nothing
        }
    }

    private KeycloakUtilizer setupKeycloakUtilizer(String username) throws ServletException {

        KeycloakUtilizer keycloakUtilizerMock = mock(KeycloakUtilizer.class);
        when(keycloakUtilizerMock.getEmail()).thenReturn(username);
        return keycloakUtilizerMock;
    }
}
