package de.fau.amos.virtualledger.server.api;


import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.savings.SavingsController;
import de.fau.amos.virtualledger.server.savings.SavingsException;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SavingsApiEndpointTest {

    @Mock
    SavingsController savingsController;

    @Test
    public void getSavingAccountsEndpoint_userPrincipalNameNull() {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);

        // ACT
        Response reponse = savingsApiEndpoint.getSavingAccountsEndpoint(context);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .getSavingAccounts(any(String.class));
    }

    @Test
    public void getSavingAccountsEndpoint_userPrincipalNameEmpty() {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);

        // ACT
        Response reponse = savingsApiEndpoint.getSavingAccountsEndpoint(context);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .getSavingAccounts(any(String.class));
    }

    @Test
    public void addSavingAccountsEndpoint_userPrincipalNameNull() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "dummy", 123.23, 453.23, new Date());

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpoint_userPrincipalNameEmpty() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "dummy", 123.23, 453.23, new Date());

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpoint_savingsAccountNull() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("username");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = null;

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }
    
    @Test
    public void addSavingAccountsEndpoint_savingsAccountNameNull() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("username");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, null, 123.43, 543.43, new Date());

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpoint_savingsAccountNameEmpty() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("username");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "", 123.43, 543.43, new Date());

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpoint_savingsAccountfinalDateNull() throws SavingsException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("username");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "test", 123.43, 543.43, null);

        // ACT
        Response reponse = savingsApiEndpoint.addSavingAccountEndpoint(context, savingsAccount);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(savingsController, times(0))
                .addSavingAccount(any(String.class), any(SavingsAccount.class));
    }
}
