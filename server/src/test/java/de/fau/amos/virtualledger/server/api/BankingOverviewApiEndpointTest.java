package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.server.auth.SimpleAuthentication;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BankingOverviewApiEndpointTest {

    @Mock
    BankingOverviewController bankingOverviewController;


    @Test
    public void getBankingOverviewEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_validInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        when(bankingOverviewController.getBankingOverview(any(String.class)))
                .thenReturn(new ArrayList<BankAccess>());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpoint_controllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        when(bankingOverviewController.getBankingOverview(any(String.class)))
                .thenThrow(new BankingException("mock"));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .getBankingOverview(any(String.class));
    }


    @Test
    public void addBankAccessEndpoint_bankAccessCredentialBankCodeNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential(null, "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialBankCodeEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialLoginNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", null, "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialLoginNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialPinNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", null));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_bankAccessCredentialPinEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", ""));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpoint_validInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }


    @Test
    public void addBankAccessEndpoint_controllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock"))
            .when(bankingOverviewController).addBankAccess(anyString(), any(BankAccessCredential.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void deleteBankAccessEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_bankAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpoint_bankAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccess(any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccessEndpoint_validInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .deleteBankAccess(any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccessEndpoint_controllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");

        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).deleteBankAccess(anyString(), anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mockId");
        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .deleteBankAccess(any(String.class), any(String.class));
    }



    @Test
    public void deleteBankAccountEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(null, "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccountIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpoint_bankAccountIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccountEndpoint_validInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }


    @Test
    public void deleteBankAccountEndpoint_controllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).deleteBankAccount(anyString(), anyString(), anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .deleteBankAccount(any(String.class), any(String.class), any(String.class));
    }












    @Test
    public void syncBankAccountEndpoint_securityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(new ArrayList<BankAccountSync>());

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_securityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(new ArrayList<BankAccountSync>());

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        bankAccountSyncList.add(null);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccoundIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", null, "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryAccountIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryPinNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", null);
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_bankAccountSyncListEntryPinEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0))
                .syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpoint_validInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .syncBankAccounts(anyString(), any(List.class));
    }


    @Test
    public void syncBankAccountEndpoint_controllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock"))
                .when(bankingOverviewController).syncBankAccounts(anyString(), any(List.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode, "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1))
                .syncBankAccounts(anyString(), any(List.class));
    }


    private void setupPrincipalUserName(String username) {

        Principal principal = mock(Principal.class);
        when(principal.getName())
                .thenReturn(username);
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal())
                .thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
