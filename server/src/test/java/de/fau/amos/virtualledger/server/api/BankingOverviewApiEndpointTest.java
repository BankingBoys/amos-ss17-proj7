package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

@RunWith(MockitoJUnitRunner.class)
public class BankingOverviewApiEndpointTest {

    @Mock
    private BankingOverviewController bankingOverviewController;

    @Test
    public void asserttrue() {
        Assert.isTrue(true, "required for mockito to not faile");
    }

/*
    @Test
    public void getBankingOverviewEndpointSecurityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpointSecurityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpointValidInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        when(bankingOverviewController.getBankingOverview(any(String.class))).thenReturn(new ArrayList<BankAccess>());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).getBankingOverview(any(String.class));
    }

    @Test
    public void getBankingOverviewEndpointControllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        when(bankingOverviewController.getBankingOverview(any(String.class))).thenThrow(new BankingException("mock"));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.getBankingOverviewEndpoint();

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).getBankingOverview(any(String.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialBankCodeNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential(null, "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointSecurityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointSecurityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialBankCodeEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("", "mock username", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialLoginNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", null, "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialLoginNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialPinNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", null));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointBankAccessCredentialPinEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", ""));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointValidInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void addBankAccessEndpointControllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock")).when(bankingOverviewController).addBankAccess(anyString(),
                any(BankAccessCredential.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .addBankAccessEndpoint(new BankAccessCredential("mock bank code", "mock login name", "mock pin"));

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).addBankAccess(any(String.class), any(BankAccessCredential.class));
    }

    @Test
    public void deleteBankAccessEndpointSecurityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpointSecurityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpointBankAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint(null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpointBankAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpointValidInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mock id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccessEndpointControllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");

        doThrow(new BankingException("mock")).when(bankingOverviewController).deleteBankAccess(anyString(),
                anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccessEndpoint("mockId");
        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).deleteBankAccess(any(String.class), any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointSecurityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id",
                "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointSecurityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id",
                "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointSankAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint(null, "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointBankAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("", "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointBankAccountIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointBankAccountIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id", "");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointValidInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id",
                "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void deleteBankAccountEndpointControllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock")).when(bankingOverviewController).deleteBankAccount(anyString(),
                anyString(), anyString());
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.deleteBankAccountEndpoint("mock access id",
                "mock account id");

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).deleteBankAccount(any(String.class), any(String.class),
                any(String.class));
    }

    @Test
    public void syncBankAccountEndpointSecurityContextPrincipalNameNull() throws BankingException {
        // SETUP
        setupPrincipalUserName(null);
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .syncBankAccountsEndpoint(new ArrayList<BankAccountSync>());

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointSecurityContextPrincipalNameEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint
                .syncBankAccountsEndpoint(new ArrayList<BankAccountSync>());

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.FORBIDDEN;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(null);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        bankAccountSyncList.add(null);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryAccessIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryAccessIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryAccoundIdNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", null, "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryAccountIdEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync(null, "", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryPinNull() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", null);
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointBankAccountSyncListEntryPinEmpty() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(0)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointValidInput() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).syncBankAccounts(anyString(), any(List.class));
    }

    @Test
    public void syncBankAccountEndpointControllerThrows() throws BankingException {
        // SETUP
        setupPrincipalUserName("test@test.de");
        doThrow(new BankingException("mock")).when(bankingOverviewController).syncBankAccounts(anyString(),
                any(List.class));
        BankingOverviewApiEndpoint bankingOverviewApiEndpoint = new BankingOverviewApiEndpoint(
                bankingOverviewController);
        List<BankAccountSync> bankAccountSyncList = new ArrayList<BankAccountSync>();
        BankAccountSync bankAccountSync = new BankAccountSync("mock access id", "mock account id", "mock pin");
        bankAccountSyncList.add(bankAccountSync);

        // ACT
        ResponseEntity<?> reponse = bankingOverviewApiEndpoint.syncBankAccountsEndpoint(bankAccountSyncList);

        // ASSERT
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;
        Assert.isTrue(reponse.getStatusCode() == expectedStatusCode,
                "Wrong status code applied! Expected " + expectedStatusCode + ", but got " + reponse.getStatusCode());
        verify(bankingOverviewController, times(1)).syncBankAccounts(anyString(), any(List.class));
    }

    private void setupPrincipalUserName(String username) {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        SimpleAuthentication authentication = mock(SimpleAuthentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }*/
}
