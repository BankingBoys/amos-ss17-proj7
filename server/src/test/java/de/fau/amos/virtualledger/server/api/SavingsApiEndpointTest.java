package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.savings.SavingsController;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SavingsApiEndpointTest {

    @Mock
    private SavingsController savingsController;
/*
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
        setupPrincipalUserName("");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
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
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
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
        setupPrincipalUserName("test@test.de");
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(savingsController);
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

    private void setupPrincipalUserName(String username) {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }*/
}
