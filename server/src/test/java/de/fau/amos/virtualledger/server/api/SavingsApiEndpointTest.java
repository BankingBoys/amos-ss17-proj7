package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.savings.SavingsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void getSavingAccountsEndpointUserPrincipalNameNull() throws ServletException {
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
    public void getSavingAccountsEndpointUserPrincipalNameEmpty() throws ServletException {
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
    public void addSavingAccountsEndpointUserPrincipalNameNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(null), savingsController);
        final int Id = 123;
        final double goalBalance = 123.23;
        final double currentBalance = 453.23;
        List<Contact> additionalContacts = new ArrayList<>();
        additionalContacts.add(new Contact());
        List<BankAccountIdentifier> bankAccountIdentifierList = new ArrayList<>();
        bankAccountIdentifierList.add(new BankAccountIdentifier());
        List<SavingsAccountSubGoal> savingsAccountSubGoalList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount("dummy", goalBalance, currentBalance, new Date(), new Date());
        savingsAccount.setAdditionalAssignedUsers(additionalContacts);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifierList);
        savingsAccount.setSubGoals(savingsAccountSubGoalList);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointUserPrincipalNameEmpty() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(""), savingsController);
        final int Id = 123;
        final double goalBalance = 123.23;
        final double currentBalance = 453.23;
        List<Contact> additionalContacts = new ArrayList<>();
        additionalContacts.add(new Contact());
        List<BankAccountIdentifier> bankAccountIdentifierList = new ArrayList<>();
        bankAccountIdentifierList.add(new BankAccountIdentifier());
        List<SavingsAccountSubGoal> savingsAccountSubGoalList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount("dummy", goalBalance, currentBalance, new Date(), new Date());
        savingsAccount.setAdditionalAssignedUsers(additionalContacts);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifierList);
        savingsAccount.setSubGoals(savingsAccountSubGoalList);

        // ACT
        ResponseEntity<?> response = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(response.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + response.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<Contact> additionalContacts = new ArrayList<>();
        additionalContacts.add(new Contact());
        List<BankAccountIdentifier> bankAccountIdentifierList = new ArrayList<>();
        bankAccountIdentifierList.add(new BankAccountIdentifier());
        List<SavingsAccountSubGoal> savingsAccountSubGoalList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount(null, goalBalance, currentBalance, new Date(), new Date());
        savingsAccount.setAdditionalAssignedUsers(additionalContacts);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifierList);
        savingsAccount.setSubGoals(savingsAccountSubGoalList);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameEmpty() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<Contact> additionalContacts = new ArrayList<>();
        additionalContacts.add(new Contact());
        List<BankAccountIdentifier> bankAccountIdentifierList = new ArrayList<>();
        bankAccountIdentifierList.add(new BankAccountIdentifier());
        List<SavingsAccountSubGoal> savingsAccountSubGoalList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount("", goalBalance, currentBalance, new Date(), new Date());
        savingsAccount.setAdditionalAssignedUsers(additionalContacts);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifierList);
        savingsAccount.setSubGoals(savingsAccountSubGoalList);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountfinalDateNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<Contact> additionalContacts = new ArrayList<>();
        additionalContacts.add(new Contact());
        List<BankAccountIdentifier> bankAccountIdentifierList = new ArrayList<>();
        bankAccountIdentifierList.add(new BankAccountIdentifier());
        List<SavingsAccountSubGoal> savingsAccountSubGoalList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount("", goalBalance, currentBalance, null, new Date());
        savingsAccount.setAdditionalAssignedUsers(additionalContacts);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifierList);
        savingsAccount.setSubGoals(savingsAccountSubGoalList);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(savingsAccount);


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccount.class));
    }


    private KeycloakUtilizer

    setupKeycloakUtilizer(String username) throws ServletException {

        KeycloakUtilizer keycloakUtilizerMock = mock(KeycloakUtilizer.class);
        when(keycloakUtilizerMock.getEmail()).thenReturn(username);
        return keycloakUtilizerMock;
    }
}
