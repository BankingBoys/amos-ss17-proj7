package de.fau.amos.virtualledger.server.banking.adorsys.api;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.DummyUserEndpoint;
import de.fau.amos.virtualledger.server.banking.model.BankingException;

@RunWith(MockitoJUnitRunner.class)
public class BankingApiFacadeTest {

    @Mock
    private BankingApiBinder bankingApiBinder;

    @Test
    public void createUserBinderCalled() {
        // SETUP
        DummyUserEndpoint endpoint = mock(DummyUserEndpoint.class);
        when(bankingApiBinder.getUserEndpoint(anyString())).thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        bankingApiFacade.createUser("test");

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1)).getUserEndpoint(anyString());
    }

    @Test
    public void getAccessesBinderCalled() {
        // SETUP
        DummyBankAccessEndpoint endpoint = mock(DummyBankAccessEndpoint.class);
        when(bankingApiBinder.getBankAccessEndpoint(anyString())).thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.getBankAccesses("test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1)).getBankAccessEndpoint(anyString());
    }

    @Test
    public void getAccountsBinderCalled() {
        // SETUP
        DummyBankAccountEndpoint endpoint = mock(DummyBankAccountEndpoint.class);
        when(bankingApiBinder.getBankAccountEndpoint(anyString())).thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.getBankAccounts("test", "test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1)).getBankAccountEndpoint(anyString());
    }

    @Test
    public void syncAccountBinderCalled() {
        // SETUP
        DummyBankAccountEndpoint endpoint = mock(DummyBankAccountEndpoint.class);
        when(bankingApiBinder.getBankAccountEndpoint(anyString())).thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.syncBankAccount("test", "test", "test", "test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1)).getBankAccountEndpoint(anyString());
    }
}
