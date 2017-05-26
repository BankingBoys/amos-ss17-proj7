package de.fau.amos.virtualledger.server.banking.adorsys.api;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.DummyUserEndpoint;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Georg on 18.05.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BankingApiFacadeTest {

    @Mock
    BankingApiBinder bankingApiBinder;


    @Test
    public void createUser_binderCalled()
    {
        // SETUP
        DummyUserEndpoint endpoint = mock(DummyUserEndpoint.class);
        when(bankingApiBinder.getUserEndpoint())
                .thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.createUser("test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getUserEndpoint();
    }

    @Test
    public void getAccesses_binderCalled()
    {
        // SETUP
        DummyBankAccessEndpoint endpoint = mock(DummyBankAccessEndpoint.class);
        when(bankingApiBinder.getBankAccessEndpoint())
                .thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.getBankAccesses("test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getBankAccessEndpoint();
    }

    @Test
    public void getAccounts_binderCalled()
    {
        // SETUP
        DummyBankAccountEndpoint endpoint = mock(DummyBankAccountEndpoint.class);
        when(bankingApiBinder.getBankAccountEndpoint())
                .thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.getBankAccounts("test", "test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getBankAccountEndpoint();
    }

    @Test
    public void syncAccount_binderCalled()
    {
        // SETUP
        DummyBankAccountEndpoint endpoint = mock(DummyBankAccountEndpoint.class);
        when(bankingApiBinder.getBankAccountEndpoint())
                .thenReturn(endpoint);
        BankingApiFacade bankingApiFacade = new BankingApiFacade(bankingApiBinder);

        // ACT
        try {
            bankingApiFacade.syncBankAccount("test", "test", "test", "test");
        } catch (BankingException ex) {
            fail("An Banking Exception was thrown! " + ex.getMessage());
        }

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getBankAccountEndpoint();
    }
}
