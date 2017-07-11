package de.fau.amos.virtualledger.server.api;


import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.auth.UserAlreadyExistsException;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationFilterTest {

    @Mock
    private AuthenticationController authenticationController;

    @Mock
    private ServletRequest servletRequest;

    @Mock
    private ServletResponse servletResponse;

    @Mock
    private FilterChain filterChain;


    @Test(expected = ServletException.class)
    public void filterEmailNull() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer(null, "first", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }

    @Test(expected = ServletException.class)
    public void filterEmailEmpty() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("", "first", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }

    @Test(expected = ServletException.class)
    public void filterFirstNameNull() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", null, "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }

    @Test(expected = ServletException.class)
    public void filterFirstNameEmpty() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }

    @Test(expected = ServletException.class)
    public void filterLastNameNull() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "first", null), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }

    @Test(expected = ServletException.class)
    public void filterLastNameEmpty() throws IOException, ServletException {
        // SETUP

        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "first", null), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
    }


    @Test
    public void filterUserNotExists() throws IOException, ServletException, VirtualLedgerAuthenticationException, UserAlreadyExistsException {
        // SETUP
        when(authenticationController.register(any(User.class))).thenReturn("test");
        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "first", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
        verify(authenticationController, times(1)).register(any(User.class));
    }

    @Test
    public void filterUserExists() throws IOException, ServletException, VirtualLedgerAuthenticationException, UserAlreadyExistsException {
        // SETUP
        when(authenticationController.register(any(User.class))).thenThrow(new UserAlreadyExistsException());
        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "first", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
        verify(authenticationController, times(1)).register(any(User.class));
    }

    @Test(expected = ServletException.class)
    public void filterVirtualLedgerAuthenticationExceptionThrown() throws IOException, ServletException, VirtualLedgerAuthenticationException, UserAlreadyExistsException {
        // SETUP
        when(authenticationController.register(any(User.class))).thenThrow(new VirtualLedgerAuthenticationException("mock"));
        UserRegistrationFilter userRegistrationFilter = new UserRegistrationFilter(
                setupKeycloakUtilizer("test@mock.de", "first", "last"), authenticationController);

        // ACT
        userRegistrationFilter.doFilter(servletRequest, servletResponse, filterChain);

        //ASSERT
        verify(authenticationController, times(1)).register(any(User.class));
    }



    private KeycloakUtilizer setupKeycloakUtilizer(String email, String firstName, String lastName) throws ServletException {

        KeycloakUtilizer keycloakUtilizerMock = mock(KeycloakUtilizer.class);
        when(keycloakUtilizerMock.getEmail()).thenReturn(email);
        when(keycloakUtilizerMock.getFirstName()).thenReturn(firstName);
        when(keycloakUtilizerMock.getLastName()).thenReturn(lastName);

        return keycloakUtilizerMock;
    }
}
