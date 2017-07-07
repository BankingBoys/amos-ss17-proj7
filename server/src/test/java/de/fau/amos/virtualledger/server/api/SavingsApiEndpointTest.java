package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.savings.SavingsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class SavingsApiEndpointTest {

    @Mock
    private SavingsController savingsController;

    @Test
    public void getSavingAccountsEndpointUserPrincipalNameNull() {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(null), savingsController);

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(""), savingsController);

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(null), savingsController);
        final int Id = 123;
        final double goalBalance = 123.23;
        final double currentBalance = 453.23;
        SavingsAccount savingsAccount = new SavingsAccount(Id, "dummy", goalBalance, currentBalance, new Date());

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(""), savingsController);
        final int Id = 123;
        final double goalBalance = 123.23;
        final double currentBalance = 453.23;
        SavingsAccount savingsAccount = new SavingsAccount(Id, "dummy", goalBalance, currentBalance, new Date());

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        SavingsAccount savingsAccount = new SavingsAccount(Id, null, goalBalance, currentBalance, new Date());

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        SavingsAccount savingsAccount = new SavingsAccount(Id, "", goalBalance, currentBalance, new Date());

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
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        SavingsAccount savingsAccount = new SavingsAccount(Id, "test", goalBalance, currentBalance, null);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    private KeycloakUtilizer setupKeycloakUtilizer(String username)  {

        KeycloakUtilizer keycloakUtilizerMock = mock(KeycloakUtilizer.class);
        when(keycloakUtilizerMock.getEmail()).thenReturn(username);
        return keycloakUtilizerMock;
    }
}
