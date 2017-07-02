package de.fau.amos.virtualledger.server.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Date;

import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import de.fau.amos.virtualledger.server.auth.SimpleAuthentication;
import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.savings.SavingsController;

@RunWith(MockitoJUnitRunner.class)
public class SavingsApiEndpointTest {

    @Mock
    private SavingsController savingsController;

    @Test
    public void getSavingAccountsEndpointUserPrincipalNameNull() {
        // SETUP
        setupPrincipalUserName(null);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.getSavingAccountsEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).getSavingAccounts(any(String.class));
    }

    @Test
    public void getSavingAccountsEndpointUserPrincipalNameEmpty() {
        // SETUP
        setupPrincipalUserName("");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.getSavingAccountsEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).getSavingAccounts(any(String.class));
    }

    @Test
    public void addSavingAccountsEndpointUserPrincipalNameNull() {
        // SETUP
        setupPrincipalUserName(null);
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "dummy", 123.23, 453.23, new Date());

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointUserPrincipalNameEmpty() {
        // SETUP
        setupPrincipalUserName("");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "dummy", 123.23, 453.23, new Date());

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNull() {
        // SETUP
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = null;

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameNull() {
        // SETUP
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, null, 123.43, 543.43, new Date());

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameEmpty() {
        // SETUP
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "", 123.43, 543.43, new Date());

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountfinalDateNull() {
        // SETUP
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
        SavingsAccount savingsAccount = new SavingsAccount(123, "test", 123.43, 543.43, null);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    private void setupPrincipalUserName(String username) {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
