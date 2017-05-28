package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Georg on 28.05.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class BankingOverviewApiEndpointTest {

    @Mock
    BankingOverviewController bankingOverviewController;


    @Test
    public void getBankingOverviewEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint(context);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint(context);

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_validInput() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        when(bankingOverviewController.getBankingOverview(any(String.class)))
                .thenReturn(new ArrayList<BankAccess>());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint(context);

        // ASSERT
        int expectedStatusCode = 200;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_controllerThrows() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        when(bankingOverviewController.getBankingOverview(any(String.class)))
                .thenThrow(new BankingException("mock"));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint(context);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .getBankingOverview(any(String.class));
    }


    @Test
    public void addBankAccessEndpoint_bankAccessCredentialBankCodeNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential(null, "mock username", "mock pin"));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialBankCodeEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("", "mock username", "mock pin"));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialLoginNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", null, "mock pin"));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialLoginNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "", "mock pin"));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialPinNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock login name", null));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialPinEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock login name", ""));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_validInput() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        int expectedStatusCode = 201;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }


    @Test
    public void addBankAccessEndpoint_controllerThrows() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        doThrow(new BankingException("mock"))
            .when(bankingOverviewController).addBankAccess(anyString(), any(BankAccessCredential.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(context, new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void deleteBankAccessEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, "mock id");

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, "mock id");

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_bankAccessIdEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, null);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_bankAccessIdNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, "");

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccessEndpoint_validInput() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, "mock id");

        // ASSERT
        int expectedStatusCode = 200;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .deleteBankAccess(any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccessEndpoint_controllerThrows() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).deleteBankAccess(anyString(), anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(context, "mockId");
        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .deleteBankAccess(any(String.class), any(String.class));
    }



    @Test
    public void deleteBankAccountEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", "mock account id");

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", "mock account id");

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccessIdEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, null, "mock account id");

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccessIdNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "", "mock account id");

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccountIdEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", null);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccountIdNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", "");

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccountEndpoint_validInput() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", "mock account id");

        // ASSERT
        int expectedStatusCode = 200;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccountEndpoint_controllerThrows() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).deleteBankAccount(anyString(), anyString(), anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(context, "mock access id", "mock account id");

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }












    @Test
    public void syncBankAccountEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(null);
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, new ArrayList<BankAccountSync>());

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, new ArrayList<BankAccountSync>());

        // ASSERT
        int expectedStatusCode = 403;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, null);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        bankAccountSyncList.add(null);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccessIdNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccessIdEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccoundIdNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", null, "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccountIdEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryPinNull() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", null);
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryPinEmpty() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_validInput() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 200;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .syncBankAccounts(anyString(), any(List.class));
    }


    @Test
    public void syncBankAccountEndpoint_controllerThrows() throws BankingException {
        // SETUP
        SecurityContext context = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn("mock");
        when(context.getUserPrincipal())
                .thenReturn(principal);
        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).syncBankAccounts(anyString(), any(List.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        Response reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(context, bankAccountSyncList);

        // ASSERT
        int expectedStatusCode = 400;
        Assert.isTrue(reponse.getStatus() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatus());
        verify(bankingOverviewController, times(1))
                .syncBankAccounts(anyString(), any(List.class));
    }
}
