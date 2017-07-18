package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountAddWrapper;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "dummy", goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointUserPrincipalNameEmpty() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer(""), savingsController);
        final int Id = 123;
        final double goalBalance = 123.23;
        final double currentBalance = 453.23;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "dummy", goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        SavingsAccountEntity savingsAccountEntity = null;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, null, goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountNameEmpty() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "", goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountfinalDateNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        bankAccountIdentifierEntityList.add(new BankAccountIdentifierEntity());
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "test", goalBalance, currentBalance, null, new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountIdentifierListNull() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = null;
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "test", goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    @Test
    public void addSavingAccountsEndpointSavingsAccountIdentifierListEmpty() throws ServletException {
        // SETUP
        SavingsApiEndpoint savingsApiEndpoint = new SavingsApiEndpoint(setupKeycloakUtilizer("test@test.de"), savingsController);
        final int Id = 123;
        final double goalBalance = 123.43;
        final double currentBalance = 543.43;
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(Id, "test", goalBalance, currentBalance, new Date(), new Date(), savingsAccountSubGoalEntitySet);

        // ACT
        ResponseEntity<?> reponse = savingsApiEndpoint.addSavingAccountEndpoint(new SavingsAccountAddWrapper(savingsAccountEntity, bankAccountIdentifierEntityList));


        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode().equals(expectedStatusCode),
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(savingsController, times(0)).addSavingAccount(any(String.class), any(SavingsAccountEntity.class), any(List.class), any(List.class));
    }

    private KeycloakUtilizer setupKeycloakUtilizer(String username) throws ServletException {

        KeycloakUtilizer keycloakUtilizerMock = mock(KeycloakUtilizer.class);
        when(keycloakUtilizerMock.getEmail()).thenReturn(username);
        return keycloakUtilizerMock;
    }
}
