package de.fau.amos.virtualledger.server.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.auth.SimpleAuthentication;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.model.UserCredential;

@RunWith(MockitoJUnitRunner.class)
public class AuthApiEndpointTest {

    @Mock
    private AuthenticationController authenticationController;

    @Mock
    private StringApiModelFactory stringApiModelFactory;

    @Before
    public void setup() {
        StringApiModel stringApiModel = new StringApiModel();
        stringApiModel.setData("mock string api model factory");
        when(stringApiModelFactory.createStringApiModel(any(String.class))).thenReturn(stringApiModel);
    }

    @Test
    public void registerEndpointCredentialIdNotZero() throws VirtualLedgerAuthenticationException {
        // SETUP
        when(authenticationController.register(any(UserCredential.class))).thenReturn("mock");
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        UserCredential credential = new UserCredential();
        credential.setEmail("test@test.de");
        credential.setPassword("password");
        credential.setFirstname("abc");
        credential.setLastname("def");
        credential.setId(1);

        // ACT
        ResponseEntity<?> reponse = authApiEndpoint.registerEndpoint(credential);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(authenticationController, times(0)).register(any(UserCredential.class));
    }

    @Test
    public void registerEndpointCredentialIdZero() throws VirtualLedgerAuthenticationException {
        // SETUP
        when(authenticationController.register(any(UserCredential.class))).thenReturn("mock");
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        UserCredential credential = new UserCredential();
        credential.setEmail("test@test.de");
        credential.setPassword("password");
        credential.setFirstname("abc");
        credential.setLastname("def");
        credential.setId(0);

        // ACT
        ResponseEntity<?> response = authApiEndpoint.registerEndpoint(credential);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(1)).register(any(UserCredential.class));
    }

    @Test
    public void registerEndpointControllerThrows() throws VirtualLedgerAuthenticationException {
        // SETUP
        when(authenticationController.register(any(UserCredential.class)))
                .thenThrow(new VirtualLedgerAuthenticationException("mock"));
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        UserCredential credential = new UserCredential();
        credential.setEmail("test@test.de");
        credential.setPassword("password");
        credential.setFirstname("abc");
        credential.setLastname("def");
        credential.setId(0);

        // ACT
        ResponseEntity<?> response = authApiEndpoint.registerEndpoint(credential);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(1)).register(any(UserCredential.class));
    }

    @Test
    public void loginEndpointEmailNull() throws InvalidCredentialsException {
        // SETUP
        SessionData sessionData = new SessionData("mock email", "mock token");
        when(authenticationController.login(any(LoginData.class))).thenReturn(sessionData);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData(null, "password");

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(0)).login(any(LoginData.class));
    }

    @Test
    public void loginEndpointEmailEmpty() throws InvalidCredentialsException {
        // SETUP
        SessionData sessionData = new SessionData("mock email", "mock token");
        when(authenticationController.login(any(LoginData.class))).thenReturn(sessionData);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData("", "password");

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(0)).login(any(LoginData.class));
    }

    @Test
    public void loginEndpointPasswordNull() throws InvalidCredentialsException {
        // SETUP
        SessionData sessionData = new SessionData("mock email", "mock token");
        when(authenticationController.login(any(LoginData.class))).thenReturn(sessionData);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData("email", null);

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(0)).login(any(LoginData.class));
    }

    @Test
    public void loginEndpointPasswordEmpty() throws InvalidCredentialsException {
        // SETUP
        SessionData sessionData = new SessionData("mock email", "mock token");
        when(authenticationController.login(any(LoginData.class))).thenReturn(sessionData);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData("email", "");

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(0)).login(any(LoginData.class));
    }

    @Test
    public void loginEndpointValidInput() throws InvalidCredentialsException {
        // SETUP
        SessionData sessionData = new SessionData("mock email", "mock token");
        when(authenticationController.login(any(LoginData.class))).thenReturn(sessionData);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData("email", "password");

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(1)).login(any(LoginData.class));
    }

    @Test
    public void loginEndpointControllerThrows() throws InvalidCredentialsException {
        // SETUP
        when(authenticationController.login(any(LoginData.class))).thenThrow(new InvalidCredentialsException());
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        LoginData loginData = new LoginData("email", "password");

        // ACT
        ResponseEntity<?> response = authApiEndpoint.loginEndpoint(loginData);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(response.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(authenticationController, times(1)).login(any(LoginData.class));
    }

    @Test
    public void logoutEndpointSecurityContextPrincipalNameNull() {
        // SETUP
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(null);
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        // ACT
        ResponseEntity<?> reponse = authApiEndpoint.logoutEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(authenticationController, times(0)).logout(any(String.class));
    }

    @Test
    public void logoutEndpointSecurityContextPrincipalNameEmpty() {
        // SETUP
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("");
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        // ACT
        ResponseEntity<?> reponse = authApiEndpoint.logoutEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(authenticationController, times(0)).logout(any(String.class));
    }

    @Test
    public void logoutEndpointValidInput() {
        // SETUP
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthApiEndpoint authApiEndpoint = new AuthApiEndpoint(authenticationController, stringApiModelFactory);

        // ACT
        ResponseEntity<?> reponse = authApiEndpoint.logoutEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(authenticationController, times(1)).logout(any(String.class));
    }
}
