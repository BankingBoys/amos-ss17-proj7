package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.model.UserCredential;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.xml.registry.infomodel.User;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by Georg on 28.05.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AuthApiEndpointTest {

    @Mock
    AuthenticationController authenticationController;

    @Mock
    StringApiModelFactory stringApiModelFactory;

    @Before
    public void setup() {
        StringApiModel stringApiModel = new StringApiModel();
        stringApiModel.setData("mock string api model factory");
        when(stringApiModelFactory.createStringApiModel(any(String.class)))
                .thenReturn(stringApiModel);
    }


    @Test
    public void registerEndpoint_credentialIdNotZero() throws VirtualLedgerAuthenticationException {
        // SETUP
        when(authenticationController.register(any(UserCredential.class)))
                .thenReturn("mock");
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        UserCredential credential = new UserCredential();
        credential.setEmail("test@test.de");
        credential.setPassword("password");
        credential.setFirstName("abc");
        credential.setLastName("def");
        credential.setId(1);

        // ACT
        Response reponse = authApiEndpoint.registerEndpoint(credential);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(authenticationController, times(0))
                .register(any(UserCredential.class));
    }

    @Test
    public void registerEndpoint_credentialIdZero() throws VirtualLedgerAuthenticationException {
        // SETUP
        when(authenticationController.register(any(UserCredential.class)))
                .thenReturn("mock");
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        UserCredential credential = new UserCredential();
        credential.setEmail("test@test.de");
        credential.setPassword("password");
        credential.setFirstName("abc");
        credential.setLastName("def");
        credential.setId(0);

        // ACT
        Response reponse = authApiEndpoint.registerEndpoint(credential);

        // ASSERT
        int expectedStatusCode = 200;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(authenticationController, times(1))
                .register(any(UserCredential.class));
    }
}
